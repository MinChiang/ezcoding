package com.ezcoding.account.extend.spring.security.authentication;

import com.ezcoding.base.web.extend.spring.security.authentication.AbstractLoginInfoPreservableAuthentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 手机+密码用户凭证
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-21 9:31
 */
public class PhonePasswordAuthentication extends AbstractLoginInfoPreservableAuthentication {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    public PhonePasswordAuthentication(String phone, String password) {
        this(null, phone, password);
    }

    public PhonePasswordAuthentication(Collection<? extends GrantedAuthority> authorities, String phone, String password) {
        super(authorities, false);
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object getCredentials() {
        return phone;
    }

    @Override
    public Object getPrincipal() {
        return password;
    }

}
