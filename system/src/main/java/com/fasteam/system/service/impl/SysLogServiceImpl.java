package com.fasteam.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasteam.common.constant.*;
import com.fasteam.common.dto.ResponseWrapper;
import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.orm.service.impl.BaseServiceImpl;
import com.fasteam.common.service.RedisService;
import com.fasteam.common.util.IPUtil;
import com.fasteam.common.util.ResponseBuilder;
import com.fasteam.common.util.WebUtils;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.configuration.AppProperties;
import com.fasteam.system.dao.SysLogDao;
import com.fasteam.system.domain.SysLog;
import com.fasteam.system.query.SysLogQuery;
import com.fasteam.system.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统日志
 */
@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLog, SysLogQuery> implements SysLogService {
    private static final Logger LOG = LoggerFactory.getLogger(SysLogServiceImpl.class);
    @Autowired
    private SysLogDao sysLogDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AppProperties appProperties;

    @Override
    public BaseDao<SysLog, SysLogQuery> getDao() {
        return sysLogDao;
    }

    @Override
    public List<Integer> listActiveUserIds(Integer activeNum, String startTime, String endTime) {
        return sysLogDao.listActiveUserIds(activeNum, startTime, endTime);
    }

    @Override
    public int countLoginSysLog(Long userId, Date timeStart) {
        return sysLogDao.countLoginSysLog(userId, timeStart);
    }

    @Override
    public void add(SysLogTypeEnum sysLogTypeEnum, Long userId, String remark, SysLogModuleEnum sysLogModuleEnum,
                    SysLogFeatureEnum sysLogFeatureEnum, SysLogActionEnum sysLogActionEnum) {
        this.add(sysLogTypeEnum, userId, remark, IPUtil.getIp(), sysLogModuleEnum, sysLogFeatureEnum, sysLogActionEnum);
    }

    @Override
    public void add(SysLogTypeEnum sysLogTypeEnum, Long userId, String remark, String ip, SysLogModuleEnum sysLogModuleEnum,
                    SysLogFeatureEnum sysLogFeatureEnum, SysLogActionEnum sysLogActionEnum) {
        SysLog sysLog = new SysLog();
        sysLog.setType(sysLogTypeEnum.getType());
        sysLog.setUserId(userId);
        sysLog.setRemark(remark);
        sysLog.setIp(ip);
        sysLog.setRequestUrl(WebUtils.getRequestURL());
        sysLog.setModule(sysLogModuleEnum != null ? sysLogModuleEnum.getCode() : null);
        sysLog.setFeature(sysLogFeatureEnum != null ? sysLogFeatureEnum.getCode() : null);
        sysLog.setAction(sysLogActionEnum != null ? sysLogActionEnum.getCode() : null);
        sysLogDao.insert(sysLog);
    }

    @Override
    public void add(Long userId, String remark, Integer type, String module, String feature, String action, String ip, String requestUrl) {
        SysLog sysLog = new SysLog();
        sysLog.setType(type);
        sysLog.setUserId(userId);
        sysLog.setRemark(remark);
        sysLog.setIp(ip);
        sysLog.setRequestUrl(requestUrl);
        sysLog.setModule(module);
        sysLog.setFeature(feature);
        sysLog.setAction(action);
        sysLogDao.insert(sysLog);
    }

    @Override
    public ResponseWrapper handlerSyslog() {
        ResponseWrapper responseWrapper;
        LOG.info("日志持久化到mysql库开始");
        List<String> sysLogList = new ArrayList<>();
        try {
            int i = 0;
            while (redisService.lSize(appProperties.sysLogKey) > 0) {
                if (i++ > 1000) {
                    break;
                }
                sysLogList.add((String) redisService.rPop(appProperties.sysLogKey));
            }
            List<SysLog> sysLogs = new ArrayList<>();
            sysLogList.forEach((sysLogString) -> {
                JSONObject sysLogJson = JSON.parseObject(sysLogString);
                SysLog sysLog = new SysLog();
                sysLog.setUserId(sysLogJson.getLong(SysLogAssistant.USER_ID));
                sysLog.setRemark(sysLogJson.getString(SysLogAssistant.REMARK));
                sysLog.setSysTime(sysLogJson.getDate(SysLogAssistant.CREATE_TIME));
                sysLog.setType(sysLogJson.getInteger(SysLogAssistant.TYPE));
                sysLog.setModule(sysLogJson.getString(SysLogAssistant.MODULE));
                sysLog.setFeature(sysLogJson.getString(SysLogAssistant.FEATURE));
                sysLog.setAction(sysLogJson.getString(SysLogAssistant.ACTION));
                sysLog.setIp(sysLogJson.getString(SysLogAssistant.IP));
                sysLog.setRequestUrl(sysLogJson.getString(SysLogAssistant.REQUEST_URL));
                sysLogs.add(sysLog);
            });
            responseWrapper = ResponseBuilder.ok();
            if (sysLogs.size() > 0) {
                sysLogDao.batchInsert(sysLogs);
            }
        } catch (Exception e) {
            LOG.error("日志持久化到mysql库失败!", e);
            sysLogList.forEach((sysLogString) -> redisService.lPush(appProperties.sysLogKey, sysLogString));
            responseWrapper = ResponseBuilder.error("日志持久化到mysql库失败!");
        }
        LOG.info("日志持久化到mysql库结束");
        return responseWrapper;
    }

    @Override
    public void batchDel(List ids) {
        super.batchDel(ids);
        LoginUser loginUser = AuthUtil.getUser();
        String remark = String.format(SysLogRemarkConst.DEL_SYS_LOG_REMARK, loginUser.getUsername(), ids.size());
        this.add(SysLogTypeEnum.MANAGER, loginUser.getId(), remark, SysLogModuleEnum.SYSTEM, SysLogFeatureEnum.FEA_SYS_LOG, SysLogActionEnum.AC_DEL);
    }
}
