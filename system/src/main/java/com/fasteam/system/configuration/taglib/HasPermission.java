package com.fasteam.system.configuration.taglib;


import com.fasteam.security.util.AuthUtil;
import com.fasteam.common.util.WebUtils;

/**
 * 权限验证
 */
public final class HasPermission {
    private HasPermission() {
    }

    /**
     * 权限验证
     *
     * @param roles 以逗号为分隔符的角色字符串
     * @return boolean
     */
    public static boolean hasPermission(String roles) {
        final String[] rolesArray = roles != null ? roles.split(",") : null;
        if (AuthUtil.hasRoles(WebUtils.getRequest(), rolesArray)) {
            return true;
        }
        return false;
    }

    /**
     * 权限验证
     *
     * @param roles 以逗号为分隔符的角色字符串
     * @return boolean
     */
    public static boolean hasAnyPermission(String roles) {
        final Object[] rolesArray = roles != null ? roles.split(",") : null;
        if (AuthUtil.hasAnyRoles(WebUtils.getRequest(), rolesArray)) {
            return true;
        }
        return false;
    }
}
