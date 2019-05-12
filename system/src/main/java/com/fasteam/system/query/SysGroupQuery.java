package com.fasteam.system.query;


import com.fasteam.common.query.BaseQuery;

/**
 * 系统用户组查询请求
 */
public class SysGroupQuery extends BaseQuery {
    private String groupCode;
    private String groupName;
    private Long fatherGroupId;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getFatherGroupId() {
        return fatherGroupId;
    }

    public void setFatherGroupId(Long fatherGroupId) {
        this.fatherGroupId = fatherGroupId;
    }
}
