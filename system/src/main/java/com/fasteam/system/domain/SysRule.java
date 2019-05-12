package com.fasteam.system.domain;



import com.fasteam.common.domain.BaseDomain;

import java.util.Date;

/**
 * 系统日志
 */
public class SysRule extends BaseDomain {
    public String ruleId;
    public String ruleExp;
    public String ruleType;
    public Long ruleStart;
    public Long ruleEnd;
    public Long currentValue;
    public String currentDate;
    public Date lastUpdate;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleExp() {
        return ruleExp;
    }

    public void setRuleExp(String ruleExp) {
        this.ruleExp = ruleExp;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public Long getRuleStart() {
        return ruleStart;
    }

    public void setRuleStart(Long ruleStart) {
        this.ruleStart = ruleStart;
    }

    public Long getRuleEnd() {
        return ruleEnd;
    }

    public void setRuleEnd(Long ruleEnd) {
        this.ruleEnd = ruleEnd;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
