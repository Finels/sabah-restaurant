package com.fasteam.system.constant;

/**
 * 用户状态
 */
public enum UserStatusEnum {
    FORMAL(1, "正式"),
    TRY(2, "试用");

    private Integer code;
    private String description;

    UserStatusEnum(Integer code, String description) {
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
