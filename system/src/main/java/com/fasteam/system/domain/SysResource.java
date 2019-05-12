package com.fasteam.system.domain;


import com.fasteam.common.domain.BaseDomain;

/**
 * 系统资源
 */
public class SysResource extends BaseDomain {
    private Long fatherId;
    private String name;
    private String uniqueCode;      //唯一编码
    private String url;
    private String icon;            //资源显示图标
    private String headIcon;        //资源头部显示图标
    private String summary;
    private Integer level;          //菜单级别 1：头部，2：二级，3:三级
    private Float weight;           //权重，排序值
    private Integer router;         //菜单跳转模式（1 路由跳转，2 超链接跳转）
    private String target;
    private Integer valid;          //是否可用 1：启用，0：禁用
    private Integer type;           //资源类型 0:系统，1：系统菜单，2：功能点
    //////////////////////////////////
    //不用持久化的字段
    private String fatherName;
    private String block;           //服务版-资源所在桌面的板块（桌面板块分几块，分别用需要1,2,3...标识）
    private String styleClass;        //服务版-资源在桌面样式
    private String authCode;        //服务版-权限编码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Integer getRouter() {
        return router;
    }

    public void setRouter(Integer router) {
        this.router = router;
    }
}
