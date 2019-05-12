package com.fasteam.system.query;

import com.fasteam.common.query.BaseQuery;
import com.fasteam.common.util.StringUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 系统日志查询请求
 */
public class SysLogQuery extends BaseQuery {
    private String userIds;
    private String remark;
    private Integer type;
    private String username;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sysTimeBegin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sysTimeEnd;

    private String timeBegin;
    private String timeEnd;

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getRemark() {
        return StringUtil.trim(remark);
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUsername() {
        return StringUtil.trim(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getSysTimeBegin() {
        return sysTimeBegin;
    }

    public void setSysTimeBegin(Date sysTimeBegin) {
        this.sysTimeBegin = sysTimeBegin;
    }

    public Date getSysTimeEnd() {
        return sysTimeEnd;
    }

    public void setSysTimeEnd(Date sysTimeEnd) {
        this.sysTimeEnd = sysTimeEnd;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        this.timeBegin = timeBegin;
    }
}
