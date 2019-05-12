package com.fasteam.system.query;


import com.fasteam.common.query.BaseQuery;
import com.fasteam.common.util.StringUtil;

/**
 * 用户请求参数
 */
public class SysUserQuery extends BaseQuery {
    private Long id;
    private String username;
    private String name;
    private Integer groupId;
    private String groupCode; //当前用户所属用户组code
    private String selectGroupCode; //查询用户时选择的用户组code
    private String groupName;
    private String roleId;
    private Integer isExpired;
    private Integer valid;
    private Integer userStatus;
    private Integer auditing;
//    private String dataArea;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return StringUtil.trim(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return StringUtil.trim(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getSelectGroupCode() {
        return selectGroupCode;
    }

    public void setSelectGroupCode(String selectGroupCode) {
        this.selectGroupCode = selectGroupCode;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Integer isExpired) {
        this.isExpired = isExpired;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getAuditing() {
        return auditing;
    }

    public void setAuditing(Integer auditing) {
        this.auditing = auditing;
    }

}
