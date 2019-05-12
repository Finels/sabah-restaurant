package com.fasteam.system.service.impl;

import com.fasteam.common.constant.*;
import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.orm.service.impl.BaseServiceImpl;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.dao.SysRoleDao;
import com.fasteam.system.domain.SysRole;
import com.fasteam.system.query.SysRoleQuery;
import com.fasteam.system.service.SysLogService;
import com.fasteam.system.service.SysRoleService;
import com.fasteam.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统角色
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, SysRoleQuery> implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public BaseDao<SysRole, SysRoleQuery> getDao() {
        return sysRoleDao;
    }

    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 通过groupCode获取该组和所有下级组的角色
     *
     * @param groupCode
     * @return
     */
    @Override
    public List<SysRole> listByGroupCode(String groupCode) {
        return sysRoleDao.listByGroupCode(groupCode);
    }

    @Override
    public Boolean checkRoleName(Long id, String name) {
        return sysRoleDao.countByName(id, name) > 0;
    }

    @Override
    public void save(final SysRole sysRole) {
        LoginUser user = AuthUtil.getUser();
        if (sysRole.getShare() == null) {
            sysRole.setShare(0);
        }

        String remark;
        SysLogActionEnum action;
        if (sysRole.getId() == null) {
            sysRole.setCreateOperator(user.getId());
            sysRoleDao.insert(sysRole);
            remark = String.format(SysLogRemarkConst.ADD_ROLE_REMARK, user.getUsername(), sysRole.getName());
            action = SysLogActionEnum.AC_ADD;
        } else {
            sysRoleDao.update(sysRole);
            remark = String.format(SysLogRemarkConst.EDIT_ROLE_REMARK, user.getUsername(), sysRole.getName());
            //同步更新用户列表中的roleCodes,roleNames
            new Thread(new Runnable() {
                public void run() {
                    sysUserService.updateRole(sysRole.getCode(), sysRole.getName(), sysRole.getId());
                }
            }).start();
            action = SysLogActionEnum.AC_UPDATE;
        }

        //记录日志
        sysLogService.add(SysLogTypeEnum.MANAGER, user.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_ROLE, action);
    }

    @Override
    public void saveAuthorization(SysRole sysRole) {
        LoginUser loginUser = AuthUtil.getUser();
        sysRoleDao.updateAuthorization(sysRole);
        String remark = String.format(SysLogRemarkConst.EDIT_ROLE_AUTHORIZATION_REMARK, loginUser.getUsername(), sysRole.getName());
        sysLogService.add(SysLogTypeEnum.MANAGER, loginUser.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_ROLE, SysLogActionEnum.AC_UPDATE);
    }

    @Override
    public List<Map<String, Object>> listFindData() {
        return sysRoleDao.listFindData();
    }

    @Override
    public void batchDel(List ids) {
        super.batchDel(ids);
        LoginUser loginUser = AuthUtil.getUser();
        String remark = String.format(SysLogRemarkConst.DEL_ROLE_REMARK, loginUser.getUsername(), ids.size());
        sysLogService.add(SysLogTypeEnum.MANAGER, loginUser.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_ROLE, SysLogActionEnum.AC_DEL);
    }
}
