package com.ezcoding.extend.spring.security.authentication;

import com.ezcoding.common.security.authentication.AbstractLoginInfoPreservableAuthentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 9:46
 */
public class NoLimitAuthentication extends AbstractLoginInfoPreservableAuthentication {

    private String code;

    public NoLimitAuthentication(String code) {
        this(null, code);
    }

    public NoLimitAuthentication(Collection<? extends GrantedAuthority> authorities, String code) {
        super(authorities, false);
        this.code = code;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
