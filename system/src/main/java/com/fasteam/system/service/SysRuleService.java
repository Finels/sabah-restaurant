package com.fasteam.system.service;


import com.fasteam.common.orm.service.BaseService;
import com.fasteam.system.domain.SysRule;
import com.fasteam.system.query.SysRuleQuery;

/**
 * 系统日志
 */
public interface SysRuleService extends BaseService<SysRule, SysRuleQuery> {

    /**
     * 获取UUID
     *
     * @return
     */
    String getUUID() throws Exception;

    /**
     * 获取线索ID
     *
     * @return
     * @throws Exception
     */
    String getClueId(String code) throws Exception;


}
