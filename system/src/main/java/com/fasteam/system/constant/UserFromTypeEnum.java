package com.fasteam.system.constant;

/**
 * 用户类型
 */
public enum UserFromTypeEnum {
    PLAT_FORM(1, "平台"),
    TOOL(2, "工具箱");

    private Integer code;
    private String description;

    UserFromTypeEnum(Integer code, String description) {
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
