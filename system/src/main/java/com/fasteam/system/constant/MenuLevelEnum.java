package com.fasteam.system.constant;

/**
 * 菜单级别
 */
public enum MenuLevelEnum {
    top(1, "一级菜单"),
    second(2, "二级菜单"),
    third(3, "三级菜单");

    private Integer code;
    private String description;

    MenuLevelEnum(Integer code, String description) {
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
