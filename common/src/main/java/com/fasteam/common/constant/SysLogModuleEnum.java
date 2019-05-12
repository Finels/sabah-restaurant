package com.fasteam.common.constant;

/**
 * Created by Administrator on 2017/11/30.
 */
public enum SysLogModuleEnum {
    WARES("wares", "商品管理"),
    SYSTEM("system", "系统管理"),
    ORDERS("orders", "订单管理");

    private String code;
    private String name;

    SysLogModuleEnum(String code, String name) {
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
