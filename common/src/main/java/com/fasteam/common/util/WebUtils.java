package com.fasteam.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:  com.fasteam.security.util
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/23
 */
public final class WebUtils {
    private WebUtils() {
    }

    public static HttpServletRequest getRequest() {
        return RequestContextHolder.getRequestAttributes() == null ? null : ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static String getCtxPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    public static String getCtxPath() {
        return getCtxPath(getRequest());
    }

    public static Object getSession() {
        return getRequest().getSession();
    }

    public static Object getSessionAttr(HttpServletRequest request, String name) {
        return request.getSession() != null ? request.getSession().getAttribute(name) : null;
    }

    public static Object getSessionAttr(String name) {
        return getSessionAttr(getRequest(), name);
    }

    public static void setSessionAttr(HttpServletRequest request, String name, Object value) {
        request.getSession().setAttribute(name, value);
    }

    public static void setSessionAttr(String name, Object value) {
        setSessionAttr(getRequest(), name, value);
    }

    public static String getRemoteAddr() {
        return getRequest().getRemoteAddr();
    }

    public static String getRequestURL() {
        return getRequest() == null ? "" : getRequestURL(getRequest());
    }

    public static String getRequestURL(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }
}
