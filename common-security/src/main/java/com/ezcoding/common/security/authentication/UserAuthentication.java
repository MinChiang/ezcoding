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
     * 用户id
     */
    private final Long id;

    public UserAuthentication(Long id, Collection<? extends GrantedAuthority> authorities, boolean authenticated) {
        super(authorities, authenticated);
        this.id = id;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.id;
    }

    public Long getId() {
        return id;
    }

}
