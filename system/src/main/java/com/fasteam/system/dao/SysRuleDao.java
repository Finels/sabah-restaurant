package com.fasteam.system.dao;


import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.system.domain.SysRule;
import com.fasteam.system.query.SysRuleQuery;

import java.util.List;

/**
 * 规则
 */
public interface SysRuleDao extends BaseDao<SysRule, SysRuleQuery> {

    /**
     * 更加ID查询编码规则
     * @param ruleId
     * @return
     */
    List<SysRule> listByRuleId(String ruleId);

    /**
     * 更新编码规则
     * @param sysRule
     */
    void update(SysRule sysRule);
}
