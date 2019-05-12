package com.fasteam.system.query;


import com.fasteam.common.query.BaseQuery;

/**
 * 角色请求参数
 */
public class SysRoleQuery extends BaseQuery {
    //    private Integer userId;
    private String name;
    private String groupCode;
//    private String dataArea;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

}
