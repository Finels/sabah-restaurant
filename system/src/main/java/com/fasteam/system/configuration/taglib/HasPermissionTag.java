package com.fasteam.system.configuration.taglib;

import com.fasteam.security.util.AuthUtil;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 *
 */
@Configuration
public class HasPermissionTag extends BodyTagSupport {
    private String roles;

    @Override
    public int doStartTag() throws JspException {
        final Object[] rolesArray = roles != null ? roles.split(",") : null;
        if (!AuthUtil.hasRoles((HttpServletRequest) pageContext.getRequest(), rolesArray)) {
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}