package com.fasteam.common.util;

import com.fasteam.common.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 全国区域
 * Created by myuser on 2017/10/19.
 */
@Component
public class AreaUtil {

    @Autowired
    private RedisService redisService;

    private static final String TOP_AREA = "100000000000";

    /**
     * 根据上级编码查区域
     *
     * @param pcode
     * @return
     */
    public List<Map<String, Object>> getAreaByPcode(String pcode) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Map<String, Object>> areaList = (List<Map<String, Object>>) redisService.get("baseAreaList");
        if (StringUtils.isEmpty(pcode)) {
            pcode = TOP_AREA;
        }
        for (int i = 0; i < areaList.size(); i++) {
            Map<String, Object> map = areaList.get(i);
            if (pcode.equals(map.get("pcode").toString())) {
                resultList.add(map);
            }
        }
        return resultList;
    }

    /**
     * 根据编码查区域
     *
     * @param code
     * @return
     */
    public Map<String, Object> getAreaByCode(String code) {
        List<Map<String, Object>> areaList = (List<Map<String, Object>>) redisService.get("baseAreaList");
        if (StringUtils.isNotEmpty(code)) {
            for (int i = 0; i < areaList.size(); i++) {
                Map<String, Object> map = areaList.get(i);
                if (code.equals(map.get("code").toString())) {
                    return map;
                }
            }
        }
        return null;
    }
}
