package com.fasteam.app.domain;

import com.fasteam.common.domain.BaseDomain;

/**
 * 菜品信息
 */
public class Dishes extends BaseDomain {
    private String name; //菜品名称
    private String introduce; //简介
    private String extra; //额外信息（与价格一一对应，逗号分隔）
    private String price; //价格（与额外项一一对应，逗号分隔）
    private Integer reserve; //库存
    private String printerLink; //关联打印机编号
    private String type; //菜品类别

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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getReserve() {
        return reserve;
    }

    public void setReserve(Integer reserve) {
        this.reserve = reserve;
    }

    public String getPrinterLink() {
        return printerLink;
    }

    public void setPrinterLink(String printerLink) {
        this.printerLink = printerLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

