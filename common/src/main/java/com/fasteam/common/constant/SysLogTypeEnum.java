package com.fasteam.common.constant;

/**
 * 日志类型
 */
public enum SysLogTypeEnum {
    LOGIN(1, "登录"),
    OPERATOR(2, "操作"),
    MANAGER(3, "管理"),
    SCORE(4, "积分");

    private Integer type;
    private String description;

    private SysLogTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
