package com.fasteam.common.constant;

/**
 * 功能类型
 */
public enum SysLogFeatureEnum {
    FEA_SYS_USER("sysUser", "用户管理"),
    FEA_SYS_GROUP("sysGroup", "用户组管理"),
    FEA_SYS_ROLE("sysRole", "角色管理"),
    FEA_SYS_RESOURCE("sysResource", "资源管理"),
    FEA_SYS_LOG("sysLog", "日志管理");

    private String code;
    private String name;

    SysLogFeatureEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
