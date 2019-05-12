package com.fasteam.system.constant;

/**
 * 审核状态
 */
public enum AuditingStatusEnum {
    NOT_REGISTERED(0, "未注册"),
    AUDIT_WAIT(1, "待审核"),
    AUDIT_PASS(3, "审核通过"),
    AUDIT_NOT_PASS(-1, "审核不通过");

    private Integer code;
    private String description;

    AuditingStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionByCode(Integer code) {
        for (AuditingStatusEnum status : AuditingStatusEnum.values()) {
            if (status.getCode().equals(code)) {
                return status.getDescription();
            }
        }
        return null;
    }
}
