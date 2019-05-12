package com.fasteam.security.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 自定义auth token
 */
public class AppAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public static final int LOGIN_MODE_DB = 0;          // 数据库验证
    public static final int LOGIN_MODE_WEBSERVICE = 1;  // webservice验证
    public static final int LOGIN_MODE_PKI = 3;       // 数字证书认证验证模式
    public static final int LOGIN_MODE_OTHER = 99;      // 其他方式

    // 登录模式
    private int loginMode;

    public AppAuthenticationToken(Object principal, Object credentials, int loginMode) {
        super(principal, credentials);
        this.loginMode = loginMode;
    }

    public int getLoginMode() {
        return loginMode;
    }

    public void setLoginMode(int loginMode) {
        this.loginMode = loginMode;
    }

}
