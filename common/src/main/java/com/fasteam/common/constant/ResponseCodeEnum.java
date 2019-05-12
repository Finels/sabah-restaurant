package com.fasteam.common.constant;

import javax.ws.rs.core.Response.Status;

/**
 * Description:  com.fasteam.appcommon.constant
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public enum ResponseCodeEnum {
    SUCCESS(Status.OK.getStatusCode(), "成功"),
    FAIL(Status.BAD_REQUEST.getStatusCode(), "失败"),
    EXIST(207, "该记录已存在");

    private int code;
    private String description;

    private ResponseCodeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}