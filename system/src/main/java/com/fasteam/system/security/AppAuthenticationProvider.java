package com.fasteam.system.security;

import com.alibaba.fastjson.JSONObject;
import com.fasteam.common.constant.BaseDataScopeEnum;
import com.fasteam.security.auth.AppAuthentication;
import com.fasteam.security.auth.AppAuthenticationToken;
import com.fasteam.security.constant.SecurityLevelConst;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.common.util.WebUtils;
import com.fasteam.system.configuration.AppProperties;
import com.fasteam.system.constant.DataAreaEnum;
import com.fasteam.system.constant.MenuLevelEnum;
import com.fasteam.system.constant.ResourceTypeEnum;
import com.fasteam.system.domain.SysResource;
import com.fasteam.system.domain.SysRole;
import com.fasteam.system.domain.SysUser;
import com.fasteam.system.service.SysResourceService;
import com.fasteam.system.service.SysRoleService;
import com.fasteam.system.service.SysUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统验证
 */
public class AppAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(AppAuthenticationProvider.class);
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysResourceService sysResourceService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AppAuthenticationToken token = (AppAuthenticationToken) authentication;
        String username = (String) token.getPrincipal();   // 用户名
        String password = (String) token.getCredentials(); // 密码

        // 重新构造令牌
        AppAuthentication nIAuthentication = new AppAuthentication(null);
        //验证用户
        Boolean normalUer = false;  //是否是正常用户
        SysUser sysUser = null;
        LOG.info("账户验证：user[{}]", username);
        if (token.getLoginMode() == AppAuthenticationToken.LOGIN_MODE_DB) {
            sysUser = sysUserService.getUserByUsernameAndPwd(username, password);
            normalUer = checkUser(sysUser, nIAuthentication);
        } else if (token.getLoginMode() == AppAuthenticationToken.LOGIN_MODE_PKI) {
            sysUser = sysUserService.getUserByUsernameAndPwdWithoutMd5(username, password);
            normalUer = checkUser(sysUser, nIAuthentication);
        }
        if (!normalUer) {
            return nIAuthentication;
        }
        LOG.info("账号验证通过！");
        LoginUser loginUser = new LoginUser();
        loginUser.setId(sysUser.getId());
        loginUser.setUsername(sysUser.getUsername());
        loginUser.setPersonal(sysUser.getPersonal());
        loginUser.setGroupId(sysUser.getGroupId());
        loginUser.setGroupCode(sysUser.getGroupCode());
        loginUser.setRoleIds(sysUser.getRoleIds());
        loginUser.setRoleCodes(sysUser.getRoleCodes());
        loginUser.setGroupName(sysUser.getGroupName());
        loginUser.setName(sysUser.getName());
        loginUser.setIdCard(sysUser.getIdCard());
        loginUser.setTel(sysUser.getTelephone());

        //设置角色权限(数据权限)
        List<SysResource> sysResources = buildRole(loginUser);

        //初始化菜单
        initMenu(sysResources, loginUser);

        //初始化系统公共配置
        initAppProperties();

        //设置topLogo
//        if (StringUtils.isNotBlank(user.getTopLogo())) {
//            WebUtils.setSessionAttr(SysConstants.TOP_LOGO, user.getTopLogo());
//        } else if (user.getFatherGroupId() != null) {
//            WebUtils.setSessionAttr(SysConstants.TOP_LOGO, getTopLogo(user.getFatherGroupId()));
//        }

        WebUtils.setSessionAttr("userId", sysUser.getId());  //TODO 临时添加，后期删除
        // 重新构造成功令牌
        nIAuthentication = new AppAuthentication(loginUser.getAuthorities());
        nIAuthentication.setDetails(loginUser);
        nIAuthentication.setPrincipal(loginUser);
//            nIAuthentication.setCredentials(password); 不保存密码
        nIAuthentication.setAuthenticated(true);
        return nIAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AppAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 验证账号是否可用
     *
     * @param user
     * @param authentication
     * @return
     */
    private Boolean checkUser(SysUser user, AppAuthentication authentication) {
        if (user == null) {
            authentication.setMessage("用户名或密码输入有误，请重新输入！");
            return false;
        }

        if (AuthUtil.isSuperAdmin(user)) {
            return true;
        }

        Date now = new Date();
        if ((user.getValidTimeBegin() != null && user.getValidTimeBegin().after(now))
                || (user.getValidTimeEnd() != null && user.getValidTimeEnd().before(now))) {
            authentication.setMessage("账号不在有效期内，请与管理员联系！");
            return false;
        }

        if (user.getLogicDel() != null && user.getLogicDel() == 1) {
            authentication.setMessage("该账户已删除！");
            return false;
        }
        return true;
    }

    /**
     * 设置角色权限
     *
     * @param user
     */
    private List<SysResource> buildRole(LoginUser user) {
        List<SysResource> sysResources = new ArrayList<>();
        //超级管理员拥有最大权限
        if (AuthUtil.isSuperAdmin(user)) {
            user.addAuthority(SecurityLevelConst.ROLE_SUPER_ADMIN);
            sysResources = sysResourceService.listUserResources(null, 1, null, null, null);
            Set<Long> resourceIds = new HashSet<>();
            for (SysResource sysResource : sysResources) {
                if (StringUtils.isNotBlank(sysResource.getAuthCode())) {
                    user.addAuthority(sysResource.getAuthCode());
                }
                resourceIds.add(sysResource.getId());
            }

            user.setResourceIds(resourceIds);
//            user.setSysResources(sysResources);
            Set<Long> userIdSet = new HashSet<>();
            if (user.getPersonal()) { //只查看个人相关信息
                userIdSet.add(user.getId());
                user.setUserIdSet(userIdSet);
            } else {
                List<SysUser> groupUserList = sysUserService.listGroupUsers(user.getGroupId());
                for (SysUser sysUser : groupUserList) {
                    userIdSet.add(sysUser.getId());
                }
                List<SysUser> lowGroupUserList = sysUserService.listLowGroupUsers(user.getGroupCode());
                for (SysUser sysUser : lowGroupUserList) {
                    userIdSet.add(sysUser.getId());
                }
                user.setUserIdSet(userIdSet);
            }

            user.setPublishShare(1);
            Set<String> dataAreaSet = new HashSet<>();
            dataAreaSet.add(DataAreaEnum.G.getCode());
            dataAreaSet.add(DataAreaEnum.L.getCode());
            dataAreaSet.add(DataAreaEnum.S.getCode());
            user.setDataArea(dataAreaSet);
            Set<String> baseDataSet = new HashSet<>();
            baseDataSet.add(BaseDataScopeEnum.ALL.getCode());
            user.setBaseDataScope(baseDataSet);
            Set<String> findDataSet = new HashSet<>();
            findDataSet.add(BaseDataScopeEnum.ALL.getCode());
            user.setFindDataScope(findDataSet);
        } else {
            if (AuthUtil.isAdmin(user)) {
                user.addAuthority(SecurityLevelConst.ROLE_ADMIN);
            } else {
                user.addAuthority(SecurityLevelConst.ROLE_USER);
            }

            //获取用户所属角色
            List<SysRole> sysRoles = getUserRoles(user.getRoleIds());
            user.setPublishShare(buildPublicShare(sysRoles));
            user.setDataArea(buildDataArea(sysRoles));
            user.setBaseDataScope(buildBaseDataScope(sysRoles));
            user.setFindDataScope(buildFindDataScope(sysRoles));
            buildUserIds(user);

            //获取用户所属角色对应的资源
            Set<Long> resourceIds = new HashSet<>();
            for (SysRole sysRole : sysRoles) {
                String resources = sysRole.getResources();
                if (StringUtils.isNotBlank(resources)) {
                    String[] resourceArray = resources.split(",");
                    for (String resource : resourceArray) {
                        if (StringUtils.isNotEmpty(resource)) {
                            resourceIds.add(Long.parseLong(resource));
                        }
                    }
                }
            }
            user.setResourceIds(resourceIds);
            if (!resourceIds.isEmpty()) {
                sysResources = sysResourceService.listUserResources(resourceIds, 1, null, null, null);
            }
//            user.setSysResources(sysResources);
            for (SysResource sysResource : sysResources) {
                if (StringUtils.isNotBlank(sysResource.getAuthCode())) {
                    user.addAuthority(sysResource.getAuthCode());
                }
            }
        }
        return sysResources;
    }

    private List<SysRole> getUserRoles(String roleIds) {
        String[] roleIdArray = StringUtils.isNotBlank(roleIds) ? roleIds.split(",") : new String[]{};
        List<Long> roleIdList = new ArrayList<>();
        for (String roleId : roleIdArray) {
            if (StringUtils.isNotEmpty(roleId)) {
                roleIdList.add(Long.parseLong(roleId));
            }
        }
        if (!roleIds.isEmpty()) {
            return sysRoleService.listByIds(roleIdList);
        }
        return new ArrayList<>();
    }

    //只要有个角色可用共享，就说明这个用户可以发布共享数据
    private int buildPublicShare(List<SysRole> sysRoles) {
        for (SysRole sysRole : sysRoles) {
            if (sysRole.getShare() != null && sysRole.getShare() == 1) {
                return 1;
            }
        }
        return 0;
    }

    private Set<String> buildDataArea(List<SysRole> sysRoles) {
        Set<String> dataAreaSet = new HashSet<>();

        for (SysRole sysRole : sysRoles) {
            if (sysRole.getDataArea() != null) {
                String[] dataAreaArray = sysRole.getDataArea().split(",");
                for (String str : dataAreaArray) {
                    dataAreaSet.add(str);
                }
            }
        }

        if (dataAreaSet.contains(DataAreaEnum.G.getCode())) { //如果包含组，则去掉个人
            dataAreaSet.remove(DataAreaEnum.P.getCode());
        }
        return dataAreaSet;
    }

    private Set<String> buildBaseDataScope(List<SysRole> sysRoles) {
        Set<String> baseDataSet = new HashSet<>();

        for (SysRole sysRole : sysRoles) {
            if (sysRole.getBaseDataScope() != null) {
                String[] baseDataArray = sysRole.getBaseDataScope().split(",");
                for (String str : baseDataArray) {
                    baseDataSet.add(str);
                }
            }
        }

        return baseDataSet;
    }

    private Set<String> buildFindDataScope(List<SysRole> sysRoles) {
        Set<String> findDataSet = new HashSet<>();

        for (SysRole sysRole : sysRoles) {
            if (sysRole.getFindDataScope() != null) {
                String[] findDataArray = sysRole.getFindDataScope().split(",");
                for (String str : findDataArray) {
                    findDataSet.add(str);
                }
            }
        }

        return findDataSet;
    }

    private void buildUserIds(LoginUser user) {
        Set<Long> userIdSeq = new HashSet<>();           //所有可见的用户Ids
        Set<Long> groupUserIdSeq = new HashSet<>();      //本机构用户Ids
        Set<Long> lowGroupUserIdSeq = new HashSet<>();   //下级机构用户Ids
        Set<Long> superGroupUserIdSeq = new HashSet<>(); // 上级机构用户Ids

        if (user.getPersonal()) {
            userIdSeq.add(user.getId());
            user.setUserIdSet(userIdSeq);
        } else {
            if (user.getDataArea().contains(DataAreaEnum.G.getCode())) {
                List<SysUser> groupUserList = sysUserService.listGroupUsers(user.getGroupId());
                for (SysUser sysUser : groupUserList) {
                    groupUserIdSeq.add(sysUser.getId());
                    userIdSeq.add(sysUser.getId());
                }
                user.setGroupUserIdSet(groupUserIdSeq);
            } else if (user.getDataArea().contains(DataAreaEnum.P.getCode())) {
                userIdSeq.add(user.getId());
            }

            if (user.getDataArea().contains(DataAreaEnum.L.getCode())) {
                List<SysUser> lowGroupUserList = sysUserService.listLowGroupUsers(user.getGroupCode());
                for (SysUser sysUser : lowGroupUserList) {
                    lowGroupUserIdSeq.add(sysUser.getId());
                    userIdSeq.add(sysUser.getId());
                }
                user.setLowGroupUserIdSet(lowGroupUserIdSeq);
            }
            user.setUserIdSet(userIdSeq);

            if (user.getDataArea().contains(DataAreaEnum.S.getCode())) {
                String superGroupCodeSeq = "";  // 上级组织机构编码序列
                String superGroupCode = "";     // 上级组织机构编码级
                String[] groupCodes = user.getGroupCode().split("_");
                if (groupCodes.length > 1) {
                    superGroupCode = groupCodes[0];
                    superGroupCodeSeq = "'" + superGroupCode + "'";
                    for (int i = 1; i < groupCodes.length - 1; i++) {
                        superGroupCode = superGroupCode + "_" + groupCodes[i];
                        superGroupCodeSeq = superGroupCodeSeq + ",'" + superGroupCode + "'";
                    }
                }
                if (superGroupCodeSeq.startsWith(",")) {
                    superGroupCodeSeq = superGroupCodeSeq.substring(1);
                }

                if (StringUtils.isNotEmpty(superGroupCodeSeq)) {
                    List<SysUser> superGroupUserList = sysUserService.listSuperGroupUsers(superGroupCodeSeq);
                    for (SysUser sysUser : superGroupUserList) {
                        superGroupUserIdSeq.add(sysUser.getId());
                    }
                    user.setSuperGroupUserIdSet(superGroupUserIdSeq);
                }
            }
        }
    }

    /**
     * 初始化菜单信息
     *
     * @param resources
     */
    private void initMenu(List<SysResource> resources, LoginUser loginUser) {
        List<JSONObject> systemResources = Lists.newArrayList();                  //系统菜单
        List<JSONObject> topMenuList = Lists.newArrayList();              //一级菜单
        Map<String, List<JSONObject>> secondMenuMap = Maps.newHashMap();   //上级uniqueCode, 子级resourceList(二级菜单)
        Map<String, List<JSONObject>> thirdMenuMap = Maps.newHashMap();    //上级uniqueCode, 子级resourceList(三级菜单)
        List<JSONObject> systemMenuList = Lists.newArrayList();              //系统管理菜单
        List<String> features = new ArrayList<>();                                               //功能按钮菜单
        //构造菜单
        for (SysResource resource : resources) {
            if (ResourceTypeEnum.system.getCode().equals(resource.getType())) {  //其它系统连接
                systemResources.add((JSONObject) JSONObject.toJSON(resource));
            } else if (ResourceTypeEnum.systemMenu.getCode().equals(resource.getType())) {  //一级菜单
                if (MenuLevelEnum.top.getCode().equals(resource.getLevel())) {
                    topMenuList.add((JSONObject) JSONObject.toJSON(resource));
                } else if (MenuLevelEnum.second.getCode().equals(resource.getLevel())) {    //二级菜单
                    SysResource fatherResource = sysResourceService.getById(resource.getFatherId());
                    if (fatherResource != null) {
                        String fatherUniqueCode = fatherResource.getUniqueCode();
                        List<JSONObject> secondSysResourceList = secondMenuMap.get(fatherUniqueCode);
                        if (secondSysResourceList == null) {
                            secondSysResourceList = new ArrayList<>();
                        }
                        secondSysResourceList.add((JSONObject) JSONObject.toJSON(resource));
                        secondMenuMap.put(fatherUniqueCode, secondSysResourceList);
                    }
                } else if (MenuLevelEnum.third.getCode().equals(resource.getLevel())) {     //三级菜单
                    SysResource fatherResource = sysResourceService.getById(resource.getFatherId());
                    if (fatherResource != null) {
                        String fatherUniqueCode = fatherResource.getUniqueCode();
                        List<JSONObject> thirdSysResourceList = thirdMenuMap.get(fatherUniqueCode);
                        if (thirdSysResourceList == null) {
                            thirdSysResourceList = new ArrayList<>();
                        }
                        thirdSysResourceList.add((JSONObject) JSONObject.toJSON(resource));
                        thirdMenuMap.put(fatherUniqueCode, thirdSysResourceList);
                    }
                }
            } else if (ResourceTypeEnum.features.getCode().equals(resource.getType())) {  //功能菜单
                features.add(resource.getAuthCode());
            }
        }
        loginUser.setSystemResourceList(systemResources);
        loginUser.setTopResourceList(topMenuList);
        loginUser.setSecondResourceMap(secondMenuMap);
        loginUser.setThirdResourceMap(thirdMenuMap);
        loginUser.setFeatures(String.join(",", features));
        systemMenuList = secondMenuMap.get("system");
        WebUtils.setSessionAttr("systemMenus", systemMenuList);
        WebUtils.setSessionAttr("features", String.join(",", features));
    }

    /**
     * 初始化系统公共配置
     */
    private void initAppProperties() {
        WebUtils.setSessionAttr("systemBaseUrl", appProperties.systemBaseUrl);
    }

    /*private String getTopLogo(Integer fatherGroupId) {
        SysGroup sysGroup = sysGroupDao.getSysGroupById(fatherGroupId);
        if (sysGroup == null) {
            return null;
        }

        if (StringUtils.isEmpty(sysGroup.getTopLogo())) {
            return getTopLogo(sysGroup.getFatherId());
        } else {
            return sysGroup.getTopLogo();
        }
    }*/
}
