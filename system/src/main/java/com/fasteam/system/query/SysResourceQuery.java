package com.fasteam.system.query;


import com.fasteam.common.query.BaseQuery;
import org.apache.commons.lang.StringUtils;

/**
 * 系统资源请求参数
 */
public class SysResourceQuery extends BaseQuery {
    private String name;
    private String uniqueCode;
    private Integer valid;
    private Integer fatherId;
    private String fatherName;

    public String getName() {
        return StringUtils.isNotEmpty(name) ? name.trim() : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueCode() {
        return StringUtils.isNotEmpty(uniqueCode) ? uniqueCode.trim() : uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

}
