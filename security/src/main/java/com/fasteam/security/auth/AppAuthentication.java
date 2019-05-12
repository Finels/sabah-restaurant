package com.fasteam.security.auth;

import com.fasteam.security.dto.LoginUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 自定义auth
 */
public class AppAuthentication extends AbstractAuthenticationToken {
    private String credentials;
    private LoginUser principal;
    private String message;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the
     *                    principal represented by this authentication object.
     */
    public AppAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public AppAuthentication(Collection<? extends GrantedAuthority> authorities, String message) {
        super(authorities);
        this.message = message;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public Object getPrincipal() {
        return principal;
    }

    public void setPrincipal(LoginUser principal) {
        this.principal = principal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
