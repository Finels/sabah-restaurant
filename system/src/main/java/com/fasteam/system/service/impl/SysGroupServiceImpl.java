package com.fasteam.system.service.impl;

import com.fasteam.common.constant.*;
import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.orm.service.impl.BaseServiceImpl;
import com.fasteam.common.tree.NodeTypeEnum;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.constant.SysConsts;
import com.fasteam.system.dao.SysGroupDao;
import com.fasteam.system.dao.SysUserDao;
import com.fasteam.system.domain.SysGroup;
import com.fasteam.system.domain.SysUser;
import com.fasteam.system.dto.SysGroupNode;
import com.fasteam.system.dto.SysObjectFatherNode;
import com.fasteam.system.dto.SysObjectNode;
import com.fasteam.system.query.SysGroupQuery;
import com.fasteam.system.service.SysGroupService;
import com.fasteam.system.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 系统用户组服务类
 */
@Service
public class SysGroupServiceImpl extends BaseServiceImpl<SysGroup, SysGroupQuery> implements SysGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(SysGroupServiceImpl.class);

    @Autowired
    private SysGroupDao sysGroupDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysLogService sysLogService;

    @Override
    public BaseDao<SysGroup, SysGroupQuery> getDao() {
        return sysGroupDao;
    }

    /**
     * 构造用户组树
     * 返回当前登录用户所在的组和它的下级组,如果数据权限只有个人或者本组，则只返回个人的那个组
     */
    public List<SysGroupNode> createTree() {
        List<SysGroupNode> rootList = new ArrayList<>();
        try {
            LoginUser loginUser = AuthUtil.getUser();
            SysGroup topGroup = sysGroupDao.getById(loginUser.getGroupId());    //每个用户对应的用户组
            if (topGroup == null) {
                return rootList;
            }

            SysGroupNode topNode = new SysGroupNode();
            topNode.setId(topGroup.getId());
            topNode.setNodeType(NodeTypeEnum.ORGAN);
            topNode.setCode(topGroup.getCode());
            topNode.setIconOpen(SysGroupNode.PARENT_ICON_OPEN);
            topNode.setIconClose(SysGroupNode.PARENT_ICON_CLOSE);
            String name = topGroup.getName();
            topNode.setTitle(name);
            if (name.length() > SysGroupNode.SHOW_LENGTH_ON_TREE) {
                name = name.substring(0, SysGroupNode.SHOW_LENGTH_ON_TREE - 1) + "...";
            }
            topNode.setName(name);

            //查看是否有子集
            SysGroupQuery sysGroupQuery = new SysGroupQuery();
            sysGroupQuery.setGroupCode(loginUser.getGroupCode());
            sysGroupQuery.setFatherGroupId(loginUser.getGroupId());
            List<SysGroup> groupList = sysGroupDao.listByQuery(sysGroupQuery);
            List<SysGroupNode> childList = new ArrayList<>();

            if (!groupList.isEmpty()) {
                topNode.setIsParent(true);
                topNode.setOpen(true);
                for (SysGroup tGroup : groupList) {
                    sysGroupQuery.setFatherGroupId(tGroup.getId());
                    List<SysGroup> cList = sysGroupDao.listByQuery(sysGroupQuery);
                    childList.add(createRoot(cList, tGroup));
                }
            } else {
                topNode.setIsParent(false);
                topNode.setOpen(true);
                topNode.setIcon(SysGroupNode.CHILD_ICON);
            }
            topNode.setChildren(childList);
            rootList.add(topNode);
        } catch (Exception e) {
            LOG.error("构造用户组树失败", e);
        }
        return rootList;
    }

    @Override
    public List<SysGroupNode> createTree(Long groupId) {
        List<SysGroupNode> rootList = new ArrayList<>();
        try {
            LoginUser loginUser = AuthUtil.getUser();
            SysGroupQuery sysGroupQuery = new SysGroupQuery();
            sysGroupQuery.setGroupCode(loginUser.getGroupCode());
            sysGroupQuery.setFatherGroupId(groupId);
            List<SysGroup> childGroups = sysGroupDao.listByQuery(sysGroupQuery);
            if (!childGroups.isEmpty()) {
                for (SysGroup clildGroup : childGroups) {
                    sysGroupQuery.setFatherGroupId(clildGroup.getId());
                    List<SysGroup> cList = sysGroupDao.listByQuery(sysGroupQuery);
                    rootList.add(createRoot(cList, clildGroup));
                }
            }
        } catch (Exception e) {
            LOG.error("根据groupId构造组织结构失败：", e);
        }
        return rootList;
    }

    @Override
    public List<SysGroupNode> createTree(String groupName) {
        List<SysGroupNode> rootList = new ArrayList<>();
        try {
            LoginUser loginUser = AuthUtil.getUser();
            SysGroupQuery sysGroupQuery = new SysGroupQuery();
            sysGroupQuery.setGroupCode(loginUser.getGroupCode());
            sysGroupQuery.setGroupName(groupName);
            List<SysGroup> sysGroups = sysGroupDao.listByQuery(sysGroupQuery);
            if (!sysGroups.isEmpty()) {
                for (SysGroup sysGroup : sysGroups) {
                    boolean haveInFatherGroup = false;
                    for (SysGroup group : sysGroups) {
                        if (sysGroup.getCode().indexOf(group.getCode() + "_") > -1) {
                            haveInFatherGroup = true;
                            break;
                        }
                    }
                    if (haveInFatherGroup) {
                        continue;
                    }
                    sysGroupQuery.setFatherGroupId(sysGroup.getId());
                    sysGroupQuery.setGroupName(null);
                    List<SysGroup> cList = sysGroupDao.listByQuery(sysGroupQuery);
                    rootList.add(createRoot(cList, sysGroup));
                }
            }
        } catch (Exception e) {
            LOG.error("根据groupName构造组织结构失败：", e);
        }
        return rootList;
    }

    private SysGroupNode createRoot(List<SysGroup> childList, SysGroup sysGroup) {
        SysGroupNode root = new SysGroupNode();
        if (!childList.isEmpty()) {
            root.setIsParent(true);
            root.setOpen(false);
            root.setIconOpen(SysGroupNode.PARENT_ICON_OPEN);
            root.setIconClose(SysGroupNode.PARENT_ICON_CLOSE);
        } else {
            root.setIsParent(false);
            root.setOpen(true);
            root.setIcon(SysGroupNode.CHILD_ICON);
        }
        root.setId(sysGroup.getId());
        root.setNodeType(NodeTypeEnum.ORGAN);
        root.setCode(sysGroup.getCode());
        String name = sysGroup.getName();
        root.setTitle(name);
        if (name.length() > SysGroupNode.SHOW_LENGTH_ON_TREE) {
            name = name.substring(0, SysGroupNode.SHOW_LENGTH_ON_TREE - 1) + "...";
        }
        root.setName(name);
        root.setUserId(sysGroup.getCreateOperator());
        return root;
    }

    @Override
    public void save(SysGroup sysGroup, MultipartFile topLogoFile) throws IOException {
        LoginUser user = AuthUtil.getUser();
        //save sysGroup
        String remark;
        SysLogActionEnum action;
        if (sysGroup.getId() == null) {
            sysGroup.setCreateOperator(user.getId());
            sysGroupDao.insert(sysGroup);
            sysGroupDao.updateCode(String.format(SysConsts.GROUP_CODE_FORMAT, sysGroup.getFatherCode(), sysGroup.getId()), sysGroup.getId());
            remark = String.format(SysLogRemarkConst.ADD_GROUP_REMARK, user.getUsername(), sysGroup.getName());
            action = SysLogActionEnum.AC_ADD;
        } else {
            sysGroup.setUpdateOperator(user.getId());
            if (!SysConsts.GROUP_TOP_CODE.equals(sysGroup.getCode())) {
                sysGroup.setCode(String.format(SysConsts.GROUP_CODE_FORMAT, sysGroup.getFatherCode(), sysGroup.getId()));
            }
            sysGroupDao.update(sysGroup);
            remark = String.format(SysLogRemarkConst.EDIT_GROUP_REMARK, user.getUsername(), sysGroup.getName());
            action = SysLogActionEnum.AC_UPDATE;
        }

//        saveLogo(topLogoFile, sysGroup);
        //记录日志
        sysLogService.add(SysLogTypeEnum.MANAGER, user.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_GROUP, action);
    }

    @Override
    public void delByCode(String code) {
        LoginUser loginUser = AuthUtil.getUser();
        sysUserDao.delByGroupCode(code);
        sysGroupDao.delByCode(code, SysConsts.GROUP_CODE_SEPARATOR);
        String remark = String.format(SysLogRemarkConst.DEL_GROUP_REMARK, loginUser.getUsername(), 1);
        sysLogService.add(SysLogTypeEnum.MANAGER, loginUser.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_GROUP, SysLogActionEnum.AC_DEL);
    }

    @Override
    public Boolean checkGroupName(Long id, String groupName) {
        return sysGroupDao.countByGroupName(id, groupName) > 0;
    }

    /*private void saveLogo(MultipartFile topLogoFile, SysGroup sysGroup) throws IOException {
        File logoDirFile = new File(logoDir);
        if (!logoDirFile.exists()) {
            logoDirFile.mkdirs();
        }

        if (topLogoFile != null && !topLogoFile.isEmpty()) {
            String topLogoName = topLogoFile.getOriginalFilename();
            String newTopLogoName = String.format(SysConsts.TOP_LOGO_NAME, sysGroup.getCode(), FileUtil.getFileLastPrefix(topLogoName));
            File newTopLogoFile = new File(logoDir + newTopLogoName);
            if (newTopLogoFile.exists()) {
                newTopLogoFile.delete();
            }
            topLogoFile.transferTo(newTopLogoFile);
            sysGroup.setTopLogo(newTopLogoName);
            sysGroupDao.updateLogo(sysGroup.getTopLogo(), sysGroup.getId());
        }
    }*/

    public SysObjectFatherNode getByFatherId(Long groupId, String userIds) {
        //获取该 groupId 下的子组织
        List<SysGroup> sysGroupList = sysGroupDao.getByFatherId(groupId);
        SysObjectFatherNode sysObjectFatherNode = new SysObjectFatherNode();
        for (SysGroup sysGroup : sysGroupList) {
            if (sysGroup.getId().equals(groupId)) {
                sysObjectFatherNode = buildNodeBySysGroup(sysGroup, null);
            } else {
                sysObjectFatherNode.addChildren(buildNodeBySysGroup(sysGroup, false));
            }
        }
        //获取该 groupId 下的子人员
        List<SysUser> sysUserList = sysUserDao.listGroupUsers(groupId);
        for (SysUser sysUser : sysUserList) {
            sysObjectFatherNode.addChildren(buildNodeBySysUser(sysUser, userIds));
        }
        //合并成 数组 返回
        return sysObjectFatherNode;
    }

    public SysObjectFatherNode getTopAndLowByFatherId(Long groupId, String userIds) {
        SysObjectFatherNode sysAllObjectFatherNode = new SysObjectFatherNode();
        SysObjectFatherNode sysObjectFatherNode = new SysObjectFatherNode();
        if (groupId == null) {
            return sysObjectFatherNode;
        }
        //获取该 groupId 下的子组织
        List<SysGroup> sysGroupList = sysGroupDao.getByFatherId(groupId);
        for (SysGroup sysGroup : sysGroupList) {
            if (sysGroup.getId().equals(groupId)) {
                sysObjectFatherNode = buildNodeBySysGroup(sysGroup, null);
            } else {
                sysObjectFatherNode.addChildren(buildNodeBySysGroup(sysGroup, false));
            }
        }
        //获取该 groupId 下的子人员
        List<SysUser> sysUserList = sysUserDao.listGroupUsers(groupId);
        for (SysUser sysUser : sysUserList) {
            sysObjectFatherNode.addChildren(buildNodeBySysUser(sysUser, userIds));
        }
        //获取上级group
        SysGroup sysGroup = sysGroupDao.getById(groupId);
        if (sysGroup != null) {
            //获取上级人员
            SysGroup topSysGroup = sysGroupDao.getByCode(sysGroup.getCode().substring(0, sysGroup.getCode().lastIndexOf("_")));
            sysAllObjectFatherNode = buildNodeBySysGroup(topSysGroup, null);
            //合并成 数组 返回
            List<SysUser> topUserList = sysUserDao.listGroupUsers(topSysGroup.getId());
            for (SysUser sysUser : topUserList) {
                sysAllObjectFatherNode.addChildren(buildNodeBySysUser(sysUser, userIds));
            }
            sysAllObjectFatherNode.addChildren(sysObjectFatherNode);
        }
        return sysAllObjectFatherNode;
    }


    private SysObjectFatherNode buildNodeBySysGroup(SysGroup sysGroup, Boolean loading) {
        SysObjectFatherNode sysObjectFatherNode = new SysObjectFatherNode();
        sysObjectFatherNode.setId(sysGroup.getId());
        sysObjectFatherNode.setType("group");
        sysObjectFatherNode.setTitle(sysGroup.getName());
        sysObjectFatherNode.setLoading(loading);
//        sysObjectFatherNode.setDisabled(true);
        sysObjectFatherNode.setDisableCheckbox(true);
        return sysObjectFatherNode;
    }

    private SysObjectNode buildNodeBySysUser(SysUser sysUser, String userIds) {
        if (sysUser.getId().equals(AuthUtil.getId())) {
            return null;
        }
        SysObjectNode sysObjectNode = new SysObjectNode();
        sysObjectNode.setId(sysUser.getId());
        sysObjectNode.setType("user");
        sysObjectNode.setTitle(sysUser.getName());
//        sysObjectNode.setDisabled(false);
        sysObjectNode.setDisableCheckbox(false);
        if (StringUtils.isNotBlank(userIds)) {
            HashSet<String> set = new HashSet<>(Arrays.asList(userIds.split(",")));
            if (set.contains(String.valueOf(sysUser.getId()))) {
                sysObjectNode.setChecked(true);
            }
        }
        return sysObjectNode;
    }

}
