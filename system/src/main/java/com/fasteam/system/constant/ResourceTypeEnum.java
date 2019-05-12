package com.fasteam.system.constant;

/**
 * 资源类型
 */
public enum ResourceTypeEnum {
    system(0, "系统"),
    systemMenu(1, "系统菜单"),
    features(2, "功能点");

    private Integer code;
    private String description;

    ResourceTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
