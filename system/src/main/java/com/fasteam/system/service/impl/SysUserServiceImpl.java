package com.fasteam.system.service.impl;


import com.fasteam.common.constant.*;
import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.orm.service.impl.BaseServiceImpl;
import com.fasteam.common.util.DateUtil;
import com.fasteam.common.util.SecurityUtil;
import com.fasteam.security.auth.AppAuthentication;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.common.util.WebUtils;
import com.fasteam.system.dao.SysUserDao;
import com.fasteam.system.domain.SysUser;
import com.fasteam.system.query.SysUserQuery;
import com.fasteam.system.service.SysLogService;
import com.fasteam.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户
 * Created by hp on 2017/9/12.
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserQuery> implements SysUserService, UserDetailsService, InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public BaseDao<SysUser, SysUserQuery> getDao() {
        return sysUserDao;
    }

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return sysUserDao.getByUsername(username);
    }

    @Override
    public SysUser getUserByUsernameAndPwd(String username, String password) {
        try {
            return sysUserDao.getByUsernameAndPwd(username, SecurityUtil.md5(password));
        } catch (Exception e) {
            LOG.error("根据用户名和密码查询用户时出错！", e);
        }
        return null;
    }

    @Override
    public SysUser getUserByUsernameAndPwdWithoutMd5(String username, String password) {
        try {
            return sysUserDao.getByUsernameAndPwd(username, password);
        } catch (Exception e) {
            LOG.error("根据用户名和密码查询用户时出错！", e);
        }
        return null;
    }

    ///////////////////////////////////
    @Override
    public Boolean checkUsername(String username) {
        return sysUserDao.countByUsername(username, 0) > 0;
    }

    @Override
    public Boolean checkUserHadDel(String username) {
        return sysUserDao.countByUsername(username, 1) > 0;
    }

    @Override
    public List<SysUser> listGroupUsers(Long groupId) {
        return sysUserDao.listGroupUsers(groupId);
    }

    @Override
    public List<SysUser> listLowGroupUsers(String groupCode) {
        return sysUserDao.listLowGroupUsers(groupCode);
    }

    @Override
    public List<SysUser> listSuperGroupUsers(String groupCodes) {
        return sysUserDao.listSuperGroupUsers(groupCodes);
    }

    @Override
    public void save(SysUser sysUser) {
        if (sysUser.getValidTimeEnd() != null) {
            sysUser.setValidTimeEnd(DateUtil.getTonightTime(sysUser.getValidTimeEnd()));
        }
        LoginUser loginUser = AuthUtil.getUser();
        String remark;
        SysLogActionEnum action;
        if (sysUser.getId() == null) {
            remark = String.format(SysLogRemarkConst.ADD_SYS_USER_REMARK, loginUser.getUsername(), sysUser.getUsername());
            action = SysLogActionEnum.AC_ADD;
            addSysUser(sysUser, loginUser);
        } else {
            remark = String.format(SysLogRemarkConst.EDIT_SYS_USER_REMARK, loginUser.getUsername(), sysUser.getUsername());
            action = SysLogActionEnum.AC_UPDATE;
            editSysUser(sysUser, loginUser);
        }
        sysLogService.add(SysLogTypeEnum.MANAGER, loginUser.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_USER, action);
    }

    @Override
    public void updateInfo(SysUser sysUser) {
        sysUserDao.updateInfo(sysUser);
    }

    private void addSysUser(SysUser sysUser, LoginUser loginUser) {
        sysUser.setUsername(sysUser.getUsername().trim());
        if (StringUtils.isNotBlank(sysUser.getPassword())) {
            sysUser.setPassword(SecurityUtil.md5(sysUser.getPassword().trim()));
        }
        sysUser.setCreateOperator(loginUser.getId());
        sysUserDao.insert(sysUser);
    }

    private void editSysUser(SysUser sysUser, LoginUser loginUser) {
        sysUser.setUpdateOperator(loginUser.getId());
        sysUserDao.update(sysUser);
//        if ("1".equals(auditing)) {
//            if (sysUser.getAuditing() != sysUser.getPreAuditing()) {
//                String remark = String.format(SysLogRemarkConst.EDIT_SYS_USER_AUDI, loginUser.getUsername(), sysUser.getUsername(),
//                        AuditingStatusEnum.getDescriptionByCode(sysUser.getPreAuditing()),
//                        AuditingStatusEnum.getDescriptionByCode(sysUser.getAuditing()));
//                sysLogService.add(SysLogTypeEnum.MANAGER, loginUser.getId(), remark,
//                        SysLogFeatureEnum.FEA_SYS_USER, SysLogActionEnum.AC_UPDATE);
//            }
//        }
    }

    @Override
    public void editPassword(Long id, String password) {
        LoginUser loginUser = AuthUtil.getUser();
        sysUserDao.updatePassword(id, password);
        if (id == loginUser.getId()) {
            String remark = String.format(SysLogRemarkConst.EDIT_PASSWORD_REMARK, loginUser.getUsername());
            sysLogService.add(SysLogTypeEnum.MANAGER, loginUser.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_USER, SysLogActionEnum.AC_UPDATE);
        }
    }

    @Override
    public void activateUser(String username) { //初始化密码为123456
        LoginUser loginUser = AuthUtil.getUser();
        sysUserDao.activateUser(SecurityUtil.md5("123456"), loginUser.getGroupId(), username);
    }

    @Override
    public void registerUser(SysUser sysUser) {
        sysUserDao.registerUser(sysUser);
        AppAuthentication authentication = (AppAuthentication) AuthUtil.getSessionAuthentication(WebUtils.getRequest());
        LoginUser loginUser = AuthUtil.getUser();
        authentication.setPrincipal(loginUser);
        AuthUtil.setSessionAuthentication(WebUtils.getRequest(), authentication);
    }

    /**
     * @param userId  更新用户Id
     * @param score   新增多少积分
     * @param remark
     * @param ip
     * @param ouserId 被更新积分的用户Id
     */
    @Override
    public void editScore(Long userId, Integer score, String remark, String ip, Long ouserId) {
//        updateScoreByUserId(ouserId, score);
//        sysLogService.addSysLog(SysLogType.SCORE, userId, remark, ip, null, null);
//        sysScoreService.addSysScore(SysScoreType.SCORE.getType(), userId, ouserId, remark, ip);
    }

    @Override
    public void editScore(Long userId, Integer score, String remark, Long ouserId) {
//        updateScoreByUserId(ouserId, score);
//        sysLogService.addSysLog(SysLogType.SCORE, userId, remark, IPUtil.getIp(), null, null);
//        sysScoreService.addSysScore(SysScoreType.SCORE.getType(), userId, ouserId, remark, IPUtil.getIp());
    }

    private void updateScoreByUserId(Long userId, Integer score) {
        sysUserDao.updateScoreByUserId(score, userId);
    }

    @Override
    public void batchDel(List ids) {
        super.batchDel(ids);
        LoginUser loginUser = AuthUtil.getUser();
        String remark = String.format(SysLogRemarkConst.DEL_SYS_USER_REMARK, loginUser.getUsername(), ids.size());
        sysLogService.add(SysLogTypeEnum.MANAGER, loginUser.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_USER, SysLogActionEnum.AC_DEL);
    }

    @Override
    public void saveLayout(String sortIds) {
        LoginUser loginUser = AuthUtil.getUser();
        sysUserDao.updateKeyTypeIdsById(sortIds, loginUser.getId());
        String remark = String.format(SysLogRemarkConst.EDIT_KEY_TYPE_LAYOUT, loginUser.getUsername());
        sysLogService.add(SysLogTypeEnum.OPERATOR, loginUser.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_USER, SysLogActionEnum.AC_UPDATE);
    }

    @Override
    public void updateRole(String code, String name, Long roleId) {
        //先取出包含roleId的所有用户
        List<SysUser> userList = sysUserDao.listByRoleId(roleId);
        if (!userList.isEmpty()) {
            for (int i = 0; i < userList.size(); i++) {
                SysUser sysUser = userList.get(i);
                String roleNames = sysUser.getRoleNames();
                String roleCodes = sysUser.getRoleCodes();
                String roleIds = sysUser.getRoleIds();

                String[] ridsStr = roleIds.split(",");
                String[] roleNamesStr = roleNames.split(",");
                String[] roleCodeStr = roleCodes.split(",");
                for (int n = 0; n < ridsStr.length; n++) {
                    String rid = ridsStr[n];
                    if (rid.equals(roleId + "")) {
                        roleCodeStr[n] = code;
                        roleNamesStr[n] = name;
                        break;
                    }
                }
                String newRoleNames = "";
                String newRoleCode = "";
                for (int n = 0; n < ridsStr.length; n++) {
                    if (n < ridsStr.length - 1) {
                        newRoleNames += roleNamesStr[n] + ",";
                        newRoleCode += roleCodeStr[n] + ",";
                    } else {
                        newRoleNames += roleNamesStr[n];
                        newRoleCode += roleCodeStr[n];
                    }
                }
                sysUserDao.updateRole(newRoleCode, newRoleNames, sysUser.getId());
            }
        }
    }

    @Override
    public LoginUser getByIdCard(String idCard) {
        return sysUserDao.getByIdCard(idCard);
    }

    @Override
    public void resetPassword(Long beginId, Long endId) {
        for (; beginId <= endId; beginId++) {
            SysUser sysUser = sysUserDao.getById(beginId);
            if (sysUser != null) {
                sysUser.setPassword(SecurityUtil.md5(sysUser.getUsername().trim()));
                sysUserDao.updatePassword(sysUser.getId(), sysUser.getPassword());
            }
        }
    }
}
