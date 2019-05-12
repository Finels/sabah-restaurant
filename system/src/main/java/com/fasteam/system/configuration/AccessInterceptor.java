package com.fasteam.system.configuration;

import com.fasteam.security.auth.Access;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * 拦截器，为ACCESS注解添加拦截器，若有此注解，则此接口对应的url需要额外配置权限才能访问
 * Created by Administrator on 2018/3/23.
 */
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if ("OPTIONS".equals(httpServletRequest.getMethod())) {
            return true;
        }

        LoginUser loginUser = AuthUtil.getUser(httpServletRequest);
        if (loginUser != null) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Access access = method.getAnnotation(Access.class);
            if (access == null) {
                return true;
            }
            if (access.authorities().length > 0) {
                String[] authorities = access.authorities();
                Set<String> authSet = new HashSet<>();
                for (String authority : authorities) {
                    authSet.add(authority);
                }
                if (AuthUtil.hasRoles(httpServletRequest, authSet.toArray())) {
                    return true;
                } else {
                    httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
