package com.fasteam.system.service;


import com.fasteam.common.constant.SysLogActionEnum;
import com.fasteam.common.constant.SysLogFeatureEnum;
import com.fasteam.common.constant.SysLogModuleEnum;
import com.fasteam.common.constant.SysLogTypeEnum;
import com.fasteam.common.dto.ResponseWrapper;
import com.fasteam.common.orm.service.BaseService;
import com.fasteam.system.domain.SysLog;
import com.fasteam.system.query.SysLogQuery;

import java.util.Date;
import java.util.List;

/**
 * 系统日志
 */
public interface SysLogService extends BaseService<SysLog, SysLogQuery> {
    List<Integer> listActiveUserIds(Integer activeNum, String startTime, String endTime);

    int countLoginSysLog(Long userId, Date timeStart);

    void add(SysLogTypeEnum sysLogTypeEnum, Long userId, String remark, SysLogModuleEnum sysLogModuleEnum,
             SysLogFeatureEnum sysLogFeatureEnum, SysLogActionEnum sysLogActionEnum);

    void add(SysLogTypeEnum sysLogTypeEnum, Long userId, String remark, String ip, SysLogModuleEnum sysLogModuleEnum,
             SysLogFeatureEnum sysLogFeatureEnum, SysLogActionEnum sysLogActionEnum);

    void add(Long userId, String remark, Integer type, String module, String feature, String action, String ip, String requestUrl);

    ResponseWrapper handlerSyslog();
}
