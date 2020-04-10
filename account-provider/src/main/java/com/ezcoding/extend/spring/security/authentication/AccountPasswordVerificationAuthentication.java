package com.ezcoding.extend.spring.security.authentication;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 用户名+密码+图形验证码用户凭证
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-21 9:31
 */
public class AccountPasswordVerificationAuthentication extends AccountPasswordAuthentication {

    /**
     * 图形验证码
     */
    private String verificationCode;

    /**
     * 回执
     */
    private String receipt;

    public AccountPasswordVerificationAuthentication(String account, String password, String verificationCode, String receipt) {
        this(null, account, password, verificationCode, receipt);
    }

    public AccountPasswordVerificationAuthentication(Collection<? extends GrantedAuthority> authorities, String account, String password, String verificationCode, String receipt) {
        super(authorities, account, password);
        this.verificationCode = verificationCode;
        this.receipt = receipt;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

}
