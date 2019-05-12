package com.fasteam.system.service.impl;

import com.fasteam.common.orm.service.impl.BaseServiceImpl;
import com.fasteam.common.util.DateUtil;
import com.fasteam.system.dao.SysRuleDao;
import com.fasteam.system.domain.SysRule;
import com.fasteam.system.query.SysRuleQuery;
import com.fasteam.system.service.SysRuleService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 系统日志
 */
@Service
public class SysRuleServiceImpl extends BaseServiceImpl<SysRule, SysRuleQuery> implements SysRuleService {
    private static final Logger LOG = LoggerFactory.getLogger(SysRuleServiceImpl.class);
    @Autowired
    private SysRuleDao sysRuleDao;
    private static final String CLUE_ID_CODE = "CLUE_ID_CODE";
    private static final Object CLUE_ID_LOCK = new Object();

    @Override
    public String getUUID() throws Exception {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    @Override
    public String getClueId(String areaCode) throws Exception {
        synchronized (CLUE_ID_LOCK) {
            return replaceParams(findSysRule(CLUE_ID_CODE), areaCode);
        }
    }

    private SysRule findSysRule(String ruleId) {
        List<SysRule> sysRules = sysRuleDao.listByRuleId(ruleId);
        Assert.notNull(sysRules, "编码规则[" + ruleId + "]不存在");
        SysRule sysRule = sysRules.get(0);
        String now = DateUtil.formatDate(new Date(), "yyyyMMdd");
        String lastDate = sysRule.getCurrentDate();
        Long currentValue = sysRule.getCurrentValue();
        if (StringUtils.isNotBlank(lastDate)) {
            if (now.compareTo(lastDate) > 0) {
                currentValue = 0L;
            } else if (now.compareTo(lastDate) < 0) {
                currentValue = 10000L;
            }
        }
        sysRule.setCurrentDate(now);
        sysRule.setCurrentValue(currentValue + 1);
        sysRuleDao.update(sysRule);
        return sysRule;
    }

    private String replaceParams(SysRule sysRule, String areaCode) {
        String exp = sysRule.getRuleExp();
        exp = exp.replaceAll("#AREA#", areaCode);
        exp = exp.replaceAll("#DATETIME#", DateUtil.formatDate(new Date(), "yyyyMMdd"));
        int i = 10;
        while (i-- > 0) {
            exp = exp.replaceAll("#SEQ" + i + "#", StringUtils.leftPad(String.valueOf(sysRule.getCurrentValue()), i, '0'));
        }
        return exp;
    }
}
