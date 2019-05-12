package com.fasteam.common.constant;

import com.alibaba.fastjson.JSONObject;
import com.fasteam.common.util.IPUtil;
import com.fasteam.common.util.WebUtils;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/7.
 */
public final class SysLogAssistant {
    private SysLogAssistant() {
    }

    public static final String USER_ID = "userId";
    public static final String REMARK = "remark";
    public static final String CREATE_TIME = "createTime";
    public static final String TYPE = "type";
    public static final String MODULE = "module";
    public static final String FEATURE = "feature";
    public static final String ACTION = "action";
    public static final String IP = "ip";
    public static final String REQUEST_URL = "requestURL";

    public static String buildSysLogMap(Long userId, String remark, SysLogTypeEnum sysLogTypeEnum, SysLogModuleEnum sysLogModuleEnum,
                                        SysLogFeatureEnum sysLogFeatureEnum, SysLogActionEnum sysLogActionEnum) {
        JSONObject sysLog = new JSONObject();
        sysLog.put(USER_ID, userId);
        sysLog.put(REMARK, remark);
        sysLog.put(CREATE_TIME, new Date());
        sysLog.put(TYPE, sysLogTypeEnum.getType());
        sysLog.put(MODULE, sysLogModuleEnum != null ? sysLogModuleEnum.getCode() : null);
        sysLog.put(FEATURE, sysLogFeatureEnum != null ? sysLogFeatureEnum.getCode() : null);
        sysLog.put(ACTION, sysLogActionEnum != null ? sysLogActionEnum.getCode() : null);
        sysLog.put(IP, IPUtil.getIp());
        sysLog.put(REQUEST_URL, WebUtils.getRequestURL());
        return sysLog.toJSONString();
    }
}
