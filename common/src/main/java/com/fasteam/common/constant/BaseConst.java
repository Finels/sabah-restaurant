package com.fasteam.common.constant;

import com.fasteam.common.util.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by aaronLin on 2018/5/1.
 */
public final class BaseConst {
    private BaseConst() {
    }

    public static final String BASE_SERVICE = "http://BASE/";
    public static final String CLUE_SERVICE = "http://CLUE/clue";
    public static final String ANALYZE_SERVICE = "http://ANALYZE/analyze";
    public static final String UNIT_SERVICE = "http://UNIT/unit";
    public static final String MAINTENANCE_SERVICE = "http://MAINTENANCE";
    public static final String MINECRAFT_SERVICE = "http://MINECRAFT";

    public static final String ENV = StringUtils.isEmpty(SpringContextUtil.getActiveProfile()) ? "prod" : SpringContextUtil.getActiveProfile();
}
