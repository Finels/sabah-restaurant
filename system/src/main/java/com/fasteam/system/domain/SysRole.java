package com.fasteam.system.domain;


import com.fasteam.common.domain.BaseDomain;

/**
 * 系统角色
 */
public class SysRole extends BaseDomain {
    private String name;
    private String code;            //角色编码
    private String resources;       //资源
    private String summary;         //角色描述
    private Integer weight;
    private Integer type;
    private Integer level;
    private String dataArea;        // 数据范围 g(group)表示本机构内，p(personal)表示个人，s(superior)表示上级，l(owner)表示下级
    private Integer share;          // 可否发布共享
    private String baseDataScope;
    private String findDataScope;
    private String findDataScopeName;
    ///////////////////////////////
    private String username;

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

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDataArea() {
        return dataArea;
    }

    public void setDataArea(String dataArea) {
        this.dataArea = dataArea;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBaseDataScope() {
        return baseDataScope;
    }

    public void setBaseDataScope(String baseDataScope) {
        this.baseDataScope = baseDataScope;
    }

    public String getFindDataScope() {
        return findDataScope;
    }

    public void setFindDataScope(String findDataScope) {
        this.findDataScope = findDataScope;
    }

    public String getFindDataScopeName() {
        return findDataScopeName;
    }

    public void setFindDataScopeName(String findDataScopeName) {
        this.findDataScopeName = findDataScopeName;
    }
}
