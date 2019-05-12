package com.fasteam.system.domain;


import com.fasteam.common.domain.BaseDomain;

/**
 * 操作日志、备注
 */
public class SysRemark extends BaseDomain {
    private Long uid;
    private Long puid;      //关联uid
    private String remark;
    private String createOperatorName;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getPuid() {
        return puid;
    }

    public void setPuid(Long puid) {
        this.puid = puid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateOperatorName() {
        return createOperatorName;
    }

    public void setCreateOperatorName(String createOperatorName) {
        this.createOperatorName = createOperatorName;
    }
}
