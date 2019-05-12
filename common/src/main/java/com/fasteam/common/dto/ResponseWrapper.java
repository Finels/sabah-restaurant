package com.fasteam.common.dto;

import com.fasteam.common.constant.ResponseCodeEnum;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Description:  com.fasteam.appcommon.dto
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public class ResponseWrapper implements Serializable {
    private static final long serialVersionUID = -4865773117792693665L;
    private int code;
    private String message;
    private Object result;
    /** @deprecated */
    @Deprecated
    private List results;
    /** @deprecated */
    @Deprecated
    private Map resultMap;
    private long total;

    public ResponseWrapper() {
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(Object code) {
        if (code instanceof ResponseCodeEnum) {
            this.code = ((ResponseCodeEnum)code).getCode();
        } else {
            this.code = Integer.valueOf(code.toString());
        }

    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /** @deprecated */
    @Deprecated
    public List getResults() {
        return this.results;
    }

    /** @deprecated */
    @Deprecated
    public void setResults(List results) {
        this.results = results;
    }

    /** @deprecated */
    @Deprecated
    public Map getResultMap() {
        return this.resultMap;
    }

    /** @deprecated */
    @Deprecated
    public void setResultMap(Map resultMap) {
        this.resultMap = resultMap;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
