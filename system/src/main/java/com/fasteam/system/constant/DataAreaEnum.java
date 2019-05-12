package com.fasteam.system.constant;

/**
 * 查看数据范围
 */
public enum DataAreaEnum {
    G("g", "可查看本组内所有用户数据"),
    P("p", "仅可查看个人数据"),
    L("l", "可查看本组以下各组的用户数据"),
    S("s", "可查看上级组共享的数据");

    private String code;
    private String description;

    DataAreaEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
