package com.fasteam.security.util;

import com.alibaba.fastjson.JSONObject;
import com.fasteam.common.util.WebUtils;
import com.fasteam.security.constant.SecurityLevelConst;
import com.fasteam.security.dto.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 权限工具类
 */
public final class AuthUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthUtil.class);

    private AuthUtil() {
    }

    /**
     * 获取登陆信息
     *
     * @param request HttpServletRequest
     * @return Authentication
     */
    public static Authentication getSessionAuthentication(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        SecurityContext securityContext = (SecurityContext) session.getAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext == null) {
            return null;
        } else {
            return securityContext.getAuthentication();
        }
    }

    /**
     * 设置登陆信息
     *
     * @param request        HttpServletRequest
     * @param authentication Authentication
     */
    public static void setSessionAuthentication(HttpServletRequest request, Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }


    /**
     * 是否已登陆
     *
     * @param authentication Authentication
     * @return boolean
     */
    public static boolean isAuthenticated(Authentication authentication) {
//        if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof LoginUser) {
//            LoginUser u = (LoginUser) authentication.getDetails();
//        }
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * 是否已登陆
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    public static boolean isAuthenticated(HttpServletRequest request) {
        return isAuthenticated(getSessionAuthentication(request));
    }

    /**
     * 是否有权限
     *
     * @param request HttpServletRequest
     * @param roles   String[] ..   eq: ROLE_USER,ROLE_ADMIn
     * @return boolean
     */

    public static boolean hasRoles(HttpServletRequest request, Object... roles) {
        Authentication sa = getSessionAuthentication(request);
        if (sa == null) {
            return false;
        }
        if (!sa.isAuthenticated()) {
            return false;
        }
        if (roles == null || roles.length == 0) {
            return true;
        }
        Collection<? extends GrantedAuthority> auths = sa.getAuthorities();
        if (auths == null) {
            return false;
        }
        for (Object role : roles) {
            boolean metched = false;
            for (GrantedAuthority auth : auths) {
                if (auth.getAuthority().equalsIgnoreCase(String.valueOf(role))) {
                    metched = true;
                }
            }
            if (!metched) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否有权限
     *
     * @param request HttpServletRequest
     * @param roles   String[] ..   eq: ROLE_USER,ROLE_ADMIn
     * @return boolean
     */
    public static boolean hasAnyRoles(HttpServletRequest request, Object... roles) {
        Authentication sa = getSessionAuthentication(request);
        if (sa == null) {
            return false;
        }
        if (!sa.isAuthenticated()) {
            return false;
        }
        if (roles == null || roles.length == 0) {
            return true;
        }
        Collection<? extends GrantedAuthority> auths = sa.getAuthorities();
        if (auths == null) {
            return false;
        }
        for (Object role : roles) {
            boolean metched = false;
            for (GrantedAuthority auth : auths) {
                if (auth.getAuthority().equalsIgnoreCase(String.valueOf(role))) {
                    metched = true;
                }
            }
            if (metched) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取用户对象
     *
     * @param request HttpServletRequest
     * @return User (not exists is null)
     */
    public static LoginUser getUser(HttpServletRequest request) {
        Authentication a = getSessionAuthentication(request);
        return (a != null && a.isAuthenticated() && a.getPrincipal() instanceof LoginUser) ? (LoginUser) a.getPrincipal() : null;
    }

    public static LoginUser getUser(SecurityContext securityContext) {
        Authentication a;
        if (securityContext == null) {
            return null;
        } else {
            a = securityContext.getAuthentication();
        }
        return (a != null && a.isAuthenticated() && a.getPrincipal() instanceof LoginUser) ? (LoginUser) a.getPrincipal() : null;
    }

    /**
     * 获取用户对象
     */
    public static LoginUser getUser() {
        Authentication a = getSessionAuthentication(WebUtils.getRequest());
        return (a != null && a.isAuthenticated() && a.getPrincipal() instanceof LoginUser) ? (LoginUser) a.getPrincipal() : null;
    }

    /**
     * 获取登陆用户ID 如(1)
     *
     * @param request HttpServletRequest
     * @return >=0  or   -1 not found
     */
    public static Long getId(HttpServletRequest request) {
        LoginUser u = getUser(request);
        return u != null ? u.getId() : -1;
    }

    /**
     * 获取登陆用户ID 如(1)
     *
     * @return >=0  or   -1 not found
     */
    public static Long getId() {
        LoginUser u = getUser(WebUtils.getRequest());
        return u != null ? u.getId() : -1;
    }

    /**
     * 获取登陆用户名 如（admin)
     *
     * @return userId or null
     */
    public static String getUserName() {
        return getUserName(WebUtils.getRequest());
    }

    /**
     * 获取登陆用户名 如（admin)
     *
     * @param request HttpServletRequest
     * @return userId or null
     */
    public static String getUserName(HttpServletRequest request) {
        LoginUser u = getUser(request);
        return u != null ? u.getUsername() : null;
    }

    public static String getName() {
        return getName(WebUtils.getRequest());
    }

    public static String getName(HttpServletRequest request) {
        LoginUser u = getUser(request);
        return u != null ? StringUtils.isNotEmpty(u.getName()) ? u.getName() : u.getUsername() : null;
    }

    /**
     * 获取用户组织编码
     */
    public static String getUserGroupCode() {
        return null;
    }

    /**
     * 是否是超级管理员
     *
     * @param request
     * @return
     */
    public static Boolean isSuperAdmin(HttpServletRequest request) {
        return isSuperAdmin(getUser(request));
    }

    public static Boolean isSuperAdmin(LoginUser user) {
        if (user != null && user.getRoleCodes() != null) {
            return user.getRoleCodes().indexOf(SecurityLevelConst.ROLE_SUPER_ADMIN) > -1;
        } else {
            return false;
        }
    }

    /**
     * 是否是管理员
     *
     * @param request
     * @return
     */
    public static Boolean isAdmin(HttpServletRequest request) {
        return isAdmin(getUser(request));
    }

    public static Boolean isAdmin(LoginUser user) {
        if (user != null && user.getRoleCodes() != null) {
            return !isSuperAdmin(user) && user.getRoleCodes().indexOf(SecurityLevelConst.ROLE_ADMIN) > -1;
        } else {
            return false;
        }
    }

    /**
     * 是否是普通用户
     *
     * @param request
     * @return
     */
    public static Boolean isUser(HttpServletRequest request) {
        return isUser(getUser(request));
    }

    public static Boolean isUser(LoginUser user) {
        if (user != null && user.getRoleCodes() != null) {
            return !isSuperAdmin(user) && !isAdmin(user) && user.getRoleCodes().indexOf(SecurityLevelConst.ROLE_USER) > -1;
        } else {
            return false;
        }
    }


    /**
     * 获取当前用户的可视ID
     */

    public static List<String> getUserIds(LoginUser loginUser) {
        List<String> idlist = new ArrayList<>();
        if (!AuthUtil.isSuperAdmin(loginUser)) {
            //管理员可以查看该分组下所有
            if (AuthUtil.isAdmin(loginUser)) {

                Set<Long> ids = loginUser.getUserIdSet();
                for (Long id : ids) {
                    idlist.add(id.toString());
                }
                ids.iterator();
            } else {
                //普通用户只能看自己
                idlist.add(loginUser.getId().toString());
            }
        }
        return idlist;
    }

//    public static Integer getBelongLevel(LoginUser loginUser) {
//        String belongCode = loginUser.getLastBelongCountyCode();
//        if (StringUtils.isBlank(belongCode)) {
//            belongCode = loginUser.getLastBelongCityCode();
//            if (StringUtils.isBlank(belongCode)) {
//                belongCode = loginUser.getLastBelongProCode();
//            }
//        }
//        Integer level = null;
//        if (AuthUtil.isSuperAdmin(loginUser)) {
//            level = 1;
//        } else {
//            if (StringUtils.isNotEmpty(belongCode)) {
//                if ("00".equals(belongCode.substring(2, 4))) {
//                    level = 1;
//                } else if ("00".equals(belongCode.substring(4, 6))) {
//                    level = 2;
//                } else {
//                    level = 3;
//                }
//            }
//        }
//        return level;
//    }

    public static String getIdCrad() {
        return getUser().getIdCard();
    }

    public static String getIdCrad(LoginUser loginUser) {
        return loginUser.getIdCard();
    }

    public static String getIdCrad(HttpServletRequest request) {
        return getUser(request).getIdCard();
    }

    public static boolean checkLogin(LoginUser loginUser, HttpServletResponse response) {
        if (loginUser != null) {
            return true;
        }
        PrintWriter writer = null;
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            writer = new PrintWriter(osw, true);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "403");
            jsonObject.put("message", "login timeout!");
            writer.write(jsonObject.toJSONString());
            writer.flush();
            writer.close();
            osw.close();
        } catch (Exception e) {
            LOGGER.error("过滤器返回信息失败！", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    LOGGER.error("过滤器返回信息失败osw.close！", e);
                }
            }
        }
        return false;
    }
}

