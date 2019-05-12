package com.fasteam.system.domain;


import com.fasteam.common.domain.BaseDomain;

/**
 * 系统用户组
 */
public class SysGroup extends BaseDomain {
    private String name;            // 组名称
    private String code;            // 组代码(NI_上级组ID_本组ID)
    private Integer fatherId;       // 上级组ID
    private String pid;             // 上级城市代码
    private String cid;             // 城市代码
    private String introduction;    // 简介
    private String contactPerson;   // 联系人姓名
    private String contactPhone;    // 联系人电话
    private String topLogo;         // 顶部logo图片
    ///////////////////////////////
    //不用持久化的字段
    private String fatherCode;
    private String fatherName;
    private String cityName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getTopLogo() {
        return topLogo;
    }

    public void setTopLogo(String topLogo) {
        this.topLogo = topLogo;
    }

    public String getFatherCode() {
        return fatherCode;
    }

    public void setFatherCode(String fatherCode) {
        this.fatherCode = fatherCode;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
