package com.ezcoding.account.extend.spring.security.authentication;

import com.ezcoding.base.web.extend.spring.security.authentication.AbstractLoginInfoPreservableAuthentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 用户名+密码用户凭证
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-21 9:31
 */
public class AccountPasswordAuthentication extends AbstractLoginInfoPreservableAuthentication {

    /**
     * 账户名
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    public AccountPasswordAuthentication(String account, String password) {
        this(null, account, password);
    }

    public AccountPasswordAuthentication(Collection<? extends GrantedAuthority> authorities, String account, String password) {
        super(authorities, false);
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getCredentials() {
        return this.password;
    }

    @Override
    public String getPrincipal() {
        return this.account;
    }

}
