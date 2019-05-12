package com.fasteam.common.constant;

/**
 * Created by linzhr on 2018/7/4.
 */
public enum BaseDataScopeEnum {
    ALL("ALL", "所有"),
    HLW("HLW", "互联网");

    private String code;
    private String name;

    BaseDataScopeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
