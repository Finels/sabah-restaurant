package com.fasteam.security.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 安全级别定义
 */
public final class SecurityLevelConst {
    private SecurityLevelConst() {
    }

    /**
     * 超级管理员，仅对开发人员开放
     */
    public static final String ROLE_SUPER_ADMIN = "superAdmin";
    /**
     * 普通管理员，可管理用户组，角色(只能修改角色权限和描述)等
     */
    public static final String ROLE_ADMIN = "admin";
    /**
     * 用户组的管理员，只能看到本组的权限，不能管理用户组，资源等。
     */
    public static final String ROLE_GROUP_ADMIN = "groupAdmin";
    /**
     * 运营人员，可管理用户组，资源，权限等，不能管理角色
     */
    public static final String ROLE_OPERATOR = "operator";
    /**
     * 普通用户，没有系统管理的权限
     */
    public static final String ROLE_USER = "user";

    public static Map<String, String> adminRoleMap = new HashMap<>();
    public static Map<String, String> userRoleMap = new HashMap<>();

    static {
        adminRoleMap.put(ROLE_ADMIN, "管理员");
        adminRoleMap.put(ROLE_USER, "普通用户");
        userRoleMap.put(ROLE_USER, "普通用户");
    }
}
