package com.fasteam.common.util;

import com.fasteam.common.constant.ResponseCodeEnum;
import com.fasteam.common.dto.ResponseWrapper;
import org.apache.commons.lang3.StringUtils;

/**
 * Description:  com.fasteam.common.util
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public final class ResponseBuilder {
    private ResponseBuilder() {
    }

    public static ResponseWrapper ok() {
        return ok((Object)null, 0L);
    }

    public static ResponseWrapper ok(Object entity) {
        return ok(entity, 0L);
    }

    public static ResponseWrapper ok(Object entity, Long total) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCodeEnum.SUCCESS);
        responseWrapper.setMessage(ResponseCodeEnum.SUCCESS.getDescription());
        responseWrapper.setResult(entity);
        responseWrapper.setTotal(total != null ? total : 0L);
        return responseWrapper;
    }

    public static ResponseWrapper error(String msg) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCodeEnum.FAIL);
        responseWrapper.setMessage(msg);
        return responseWrapper;
    }

    public static ResponseWrapper error(int errorCode, String msg) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(errorCode);
        responseWrapper.setMessage(msg);
        return responseWrapper;
    }

    public static ResponseWrapper error(Exception e, String defaultMsg) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCodeEnum.FAIL);
        if (StringUtils.isNotEmpty(defaultMsg)) {
            responseWrapper.setMessage(defaultMsg);
        } else {
            responseWrapper.setMessage(e.getMessage());
        }

        return responseWrapper;
    }

    public static ResponseWrapper exist(String msg) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCodeEnum.EXIST);
        responseWrapper.setMessage(msg);
        return responseWrapper;
    }

    public static ResponseWrapper customerError(Exception e, String defaultMsg) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCodeEnum.FAIL);
        if (!(e instanceof IllegalStateException) && !(e instanceof IllegalArgumentException) && !(e instanceof NullPointerException) && !(e instanceof IndexOutOfBoundsException)) {
            responseWrapper.setMessage(defaultMsg);
        } else {
            responseWrapper.setMessage(e.getMessage());
        }

        return responseWrapper;
    }
}
