package com.ezcoding.common.security.authentication;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-21 21:42
 */
public class UserAuthentication extends AbstractLoginInfoPreservableAuthentication {

    /**
     * 用户编号
     */
    private String userCode;

    public UserAuthentication(String userCode, Collection<? extends GrantedAuthority> authorities, boolean authenticated) {
        super(authorities, authenticated);
        this.userCode = userCode;
    }

    @Override
    public Object getCredentials() {
        return "N/A";
    }

    @Override
    public Object getPrincipal() {
        return this.userCode;
    }

    @Override
    public String getName() {
        return this.userCode;
    }

}
