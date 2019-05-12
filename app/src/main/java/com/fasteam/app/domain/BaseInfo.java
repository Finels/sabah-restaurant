package com.fasteam.app.domain;

import com.fasteam.common.domain.BaseDomain;

/**
 * 商铺基本信息
 */
public class BaseInfo extends BaseDomain {
    private String name; //商铺名称
    private String introduce; //简介
    private String tel; //联系电话
    private String address; //地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

