package com.fasteam.system.query;


import com.fasteam.common.query.BaseQuery;
import com.fasteam.common.util.StringUtil;

/**
 * 备注查询请求
 */
public class SysRemarkQuery extends BaseQuery {

    private String remark;
    private Long puid;

    public String getRemark() {
        return StringUtil.trim(remark);
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getPuid() {
        return puid;
    }

    public void setPuid(Long puid) {
        this.puid = puid;
    }
}
