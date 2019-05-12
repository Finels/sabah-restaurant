package com.fasteam.system.service.impl;

import com.fasteam.common.constant.*;
import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.orm.service.impl.BaseServiceImpl;
import com.fasteam.security.constant.SecurityLevelConst;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.constant.SysConsts;
import com.fasteam.system.dao.SysResourceDao;
import com.fasteam.system.domain.SysResource;
import com.fasteam.system.dto.SysResourceNode;
import com.fasteam.system.query.SysResourceQuery;
import com.fasteam.system.service.SysLogService;
import com.fasteam.system.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统资源
 */
@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource, SysResourceQuery> implements SysResourceService {
    public static Set<String> userResourceNotShows = new HashSet<>();     //普通用户授权时不显示的资源菜单code
    public static Set<String> adminResourceNotShows = new HashSet<>();    //管理员授权时不显示的资源菜单code

    static {
        userResourceNotShows.add("sysUser_add");
        userResourceNotShows.add("sysUser_edit");
        userResourceNotShows.add("sysUser_del");
        userResourceNotShows.add("sysGroup");
        userResourceNotShows.add("sysRole");
        userResourceNotShows.add("sysRole_add");
        userResourceNotShows.add("sysRole_del");
        userResourceNotShows.add("sysRole_edit");
        userResourceNotShows.add("sysRole_auth");
        userResourceNotShows.add("sysResource");
        adminResourceNotShows.add("sysResource");
    }

    @Autowired
    private SysResourceDao sysResourceDao;

    @Override
    public BaseDao<SysResource, SysResourceQuery> getDao() {
        return sysResourceDao;
    }

    @Autowired
    private SysLogService sysLogService;

    //    @Cacheable(value = "sysResource", key = "#id")
    @Override
    public SysResource getById(Long id) {
        return this.getDao().getById(id);
    }

    @Override
    public List<SysResource> listUserResources(Set<Long> ids, Integer valid, Long fatherId, Integer level, Integer type) {
        return sysResourceDao.listUserResources(ids, valid, fatherId, level, type);
    }

    //    @CacheEvict(value = "niCache", key = "'sysResource_'+#sysResource.id", beforeInvocation = true)
    @Override
    public void save(SysResource sysResource) {
        LoginUser user = AuthUtil.getUser();

        if (sysResource.getFatherId() == null) {
            sysResource.setFatherId(Long.parseLong(SysConsts.RESOURCE_DEFAULT_FATHER_ID));
        }

        String remark;
        SysLogActionEnum action;
        if (sysResource.getId() == null) {
            sysResource.setCreateOperator(user.getId());
            sysResourceDao.insert(sysResource);
            remark = String.format(SysLogRemarkConst.ADD_RESOURCE_REMARK, user.getUsername(), sysResource.getName());
            action = SysLogActionEnum.AC_ADD;
        } else {
            sysResource.setUpdateOperator(user.getId());
            sysResourceDao.update(sysResource);
            remark = String.format(SysLogRemarkConst.EDIT_RESOURCE_REMARK, user.getUsername(), sysResource.getName());
            action = SysLogActionEnum.AC_UPDATE;
        }
        sysLogService.add(SysLogTypeEnum.MANAGER, user.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_RESOURCE, action);
    }

    @Override
    public void batchDel(List ids) {
        super.batchDel(ids);
        LoginUser loginUser = AuthUtil.getUser();
        //记录日志
        String remark = String.format(SysLogRemarkConst.DEL_RESOURCE_REMARK, loginUser.getUsername(), ids.size());
        sysLogService.add(SysLogTypeEnum.MANAGER, loginUser.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_RESOURCE, SysLogActionEnum.AC_DEL);
    }

    @Override
    public Boolean checkUniqueCode(Long id, String uniqueCode) {
        return sysResourceDao.countSysResourceByUniqueCode(id, uniqueCode) > 0;
    }

    /**
     * 返回资源树
     *
     * @param selectResources 已被选中的资源
     *                        flag true: 获取所有,包括子节点, false 不包括子节点
     * @param roleCode        角色code
     */
    public List<SysResourceNode> createResourceTree(Long fatherId, Boolean flag, List<String> selectResources, String roleCode) {
        LoginUser loginUser = AuthUtil.getUser();
        Set<Long> resourceIds = loginUser.getResourceIds();

        List<SysResourceNode> treeList = new ArrayList<>();
        List<SysResource> resourceList = sysResourceDao.listUserResources(null, null, fatherId, null, null);
        if (resourceList != null && resourceList.size() > 0) {
            for (SysResource clildResource : resourceList) {
                if ((SecurityLevelConst.ROLE_ADMIN.equals(roleCode) && adminResourceNotShows.contains(clildResource.getUniqueCode()))
                        || (SecurityLevelConst.ROLE_USER.equals(roleCode) && userResourceNotShows.contains(clildResource.getUniqueCode()))) {
                    continue;
                }
                if (resourceIds.contains(clildResource.getId())) {
                    SysResourceNode root = new SysResourceNode();
                    List<SysResource> cList = sysResourceDao.listUserResources(null, null, clildResource.getId(), null, null);

                    if (!cList.isEmpty()) {
                        root.setIsParent(true);
                        root.setOpen(false);
                    } else {
                        root.setIsParent(false);
                        root.setOpen(true);
                    }
                    root.setId(clildResource.getId());
                    String name = clildResource.getName();
                    root.setTitle(name);
                    if (name.length() > SysResourceNode.SHOW_LENGTH_ON_TREE) {
                        name = name.substring(0, SysResourceNode.SHOW_LENGTH_ON_TREE - 1) + "...";
                    }
                    root.setName(name);

                    if (selectResources != null && selectResources.contains(String.valueOf(clildResource.getId()))) {
                        root.setChecked(true);
                    }
                    if (flag) {
                        root.setChildren(createResourceTree(root.getId(), flag, selectResources, roleCode));
                    }
                    treeList.add(root);
                }
            }
        }
        return treeList;
    }

    @Override
    public List<SysResource> listResourceByUniqueCode(String uniqueCode) {
        return sysResourceDao.listResourceByUniqueCode(uniqueCode);
    }
}
