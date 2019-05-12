package com.fasteam.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:  com.fasteam.appcommon.util
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public class BaiduCoordinatesUtil {
    private static Double EARTHRADIUS = 6370996.81D;
    private static Double[] MCBAND = new Double[]{1.289059486E7D, 8362377.87D, 5591021.0D, 3481989.83D, 1678043.12D, 0.0D};
    private static Double[] LLBAND = new Double[]{75.0D, 60.0D, 45.0D, 30.0D, 15.0D, 0.0D};
    private static Double[][] MC2LL = new Double[][]{{1.410526172116255E-8D, 8.98305509648872E-6D, -1.9939833816331D, 200.9824383106796D, -187.2403703815547D, 91.6087516669843D, -23.38765649603339D, 2.57121317296198D, -0.03801003308653D, 1.73379812E7D}, {-7.435856389565537E-9D, 8.983055097726239E-6D, -0.78625201886289D, 96.32687599759846D, -1.85204757529826D, -59.36935905485877D, 47.40033549296737D, -16.50741931063887D, 2.28786674699375D, 1.026014486E7D}, {-3.030883460898826E-8D, 8.98305509983578E-6D, 0.30071316287616D, 59.74293618442277D, 7.357984074871D, -25.38371002664745D, 13.45380521110908D, -3.29883767235584D, 0.32710905363475D, 6856817.37D}, {-1.981981304930552E-8D, 8.983055099779535E-6D, 0.03278182852591D, 40.31678527705744D, 0.65659298677277D, -4.44255534477492D, 0.85341911805263D, 0.12923347998204D, -0.04625736007561D, 4482777.06D}, {3.09191371068437E-9D, 8.983055096812155E-6D, 6.995724062E-5D, 23.10934304144901D, -2.3663490511E-4D, -0.6321817810242D, -0.00663494467273D, 0.03430082397953D, -0.00466043876332D, 2555164.4D}, {2.890871144776878E-9D, 8.983055095805407E-6D, -3.068298E-8D, 7.47137025468032D, -3.53937994E-6D, -0.02145144861037D, -1.234426596E-5D, 1.0322952773E-4D, -3.23890364E-6D, 826088.5D}};
    private static Double[][] LL2MC = new Double[][]{{-0.0015702102444D, 111320.7020616939D, 1.704480524535203E15D, -1.033898737604234E16D, 2.611266785660388E16D, -3.51496691766537E16D, 2.659570071840392E16D, -1.072501245418824E16D, 1.800819912950474E15D, 82.5D}, {8.277824516172526E-4D, 111320.7020463578D, 6.477955746671607E8D, -4.082003173641316E9D, 1.077490566351142E10D, -1.517187553151559E10D, 1.205306533862167E10D, -5.124939663577472E9D, 9.133119359512032E8D, 67.5D}, {0.00337398766765D, 111320.7020202162D, 4481351.045890365D, -2.339375119931662E7D, 7.968221547186455E7D, -1.159649932797253E8D, 9.723671115602145E7D, -4.366194633752821E7D, 8477230.501135234D, 52.5D}, {0.00220636496208D, 111320.7020209128D, 51751.86112841131D, 3796837.749470245D, 992013.7397791013D, -1221952.21711287D, 1340652.697009075D, -620943.6990984312D, 144416.9293806241D, 37.5D}, {-3.441963504368392E-4D, 111320.7020576856D, 278.2353980772752D, 2485758.690035394D, 6070.750963243378D, 54821.18345352118D, 9540.606633304236D, -2710.55326746645D, 1405.483844121726D, 22.5D}, {-3.218135878613132E-4D, 111320.7020701615D, 0.00369383431289D, 823725.6402795718D, 0.46104986909093D, 2351.343141331292D, 1.58060784298199D, 8.77738589078284D, 0.37238884252424D, 7.45D}};

    public BaiduCoordinatesUtil() {
    }

    public static void main(String[] args) {
        Map<String, Double> location = convertMC2LL(1.3745329E7D, 5104310.5D);
        System.out.println(location.get("lng") + "===" + location.get("lat"));
        location.put("lng", 118.13454D);
        location.put("lat", 24.468729D);
        location = convertLL2MC((Double)location.get("lng"), (Double)location.get("lat"));
        System.out.println(location.get("x") + "===" + location.get("y"));
    }

    public static Map<String, Double> convertMC2LL(Double x, Double y) {
        Double[] cF = null;
        x = Math.abs(x);
        y = Math.abs(y);

        for(int cE = 0; cE < MCBAND.length; ++cE) {
            if (y >= MCBAND[cE]) {
                cF = MC2LL[cE];
                break;
            }
        }

        Map<String, Double> location = converter(x, y, cF);
        location.put("lng", location.get("x"));
        location.remove("x");
        location.put("lat", location.get("y"));
        location.remove("y");
        return location;
    }

    public static Map<String, Double> convertLL2MC(Double lng, Double lat) {
        Double[] cE = null;
        lng = getLoop(lng, -180, 180);
        lat = getRange(lat, -74, 74);

        int i;
        for(i = 0; i < LLBAND.length; ++i) {
            if (lat >= LLBAND[i]) {
                cE = LL2MC[i];
                break;
            }
        }

        if (cE != null) {
            for(i = LLBAND.length - 1; i >= 0; --i) {
                if (lat <= -LLBAND[i]) {
                    cE = LL2MC[i];
                    break;
                }
            }
        }

        return converter(lng, lat, cE);
    }

    private static Map<String, Double> converter(Double x, Double y, Double[] cE) {
        Double xTemp = cE[0] + cE[1] * Math.abs(x);
        Double cC = Math.abs(y) / cE[9];
        Double yTemp = cE[2] + cE[3] * cC + cE[4] * cC * cC + cE[5] * cC * cC * cC + cE[6] * cC * cC * cC * cC + cE[7] * cC * cC * cC * cC * cC + cE[8] * cC * cC * cC * cC * cC * cC;
        xTemp = xTemp * (double)(x < 0.0D ? -1 : 1);
        yTemp = yTemp * (double)(y < 0.0D ? -1 : 1);
        Map<String, Double> location = new HashMap();
        location.put("x", xTemp);
        location.put("y", yTemp);
        return location;
    }

    private static Double getLoop(Double lng, Integer min, Integer max) {
        while(lng > (double)max) {
            lng = lng - (double)(max - min);
        }

        while(lng < (double)min) {
            lng = lng + (double)(max - min);
        }

        return lng;
    }

    private static Double getRange(Double lat, Integer min, Integer max) {
        if (min != null) {
            lat = Math.max(lat, (double)min);
        }

        if (max != null) {
            lat = Math.min(lat, (double)max);
        }

        return lat;
    }
}
