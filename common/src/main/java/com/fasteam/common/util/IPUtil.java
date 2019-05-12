package com.fasteam.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Description:  com.fasteam.common.util
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public final class IPUtil {
    public static final String UNKNOWN = "unknown";
    public static final String LOCALHOST_IPV4 = "127.0.0.1";
    public static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    private IPUtil() {
    }

    public static String getIp() {
        return WebUtils.getRequest() == null ? "" : getIp(WebUtils.getRequest());
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isInvalidIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (isInvalidIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (isInvalidIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (isInvalidIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (isInvalidIp(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException var3) {
                    ip = "";
                }
            }
        }

        if (ip != null && ip.length() > 15) {
            int commaIndex = ip.indexOf(",");
            if (commaIndex > 0) {
                ip = ip.substring(0, commaIndex);
            }
        }

        return ip;
    }

    private static boolean isInvalidIp(String ip) {
        return StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip);
    }
}
