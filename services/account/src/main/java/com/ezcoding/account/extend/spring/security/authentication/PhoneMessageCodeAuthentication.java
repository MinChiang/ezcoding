package com.ezcoding.account.extend.spring.security.authentication;

import com.ezcoding.base.web.extend.spring.security.authentication.AbstractLoginInfoPreservableAuthentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 手机+验证码用户凭证
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-21 9:31
 */
public class PhoneMessageCodeAuthentication extends AbstractLoginInfoPreservableAuthentication {

    /**
     * 回执
     */
    private String receipt;

    /**
     * 用户标志
     */
    private String phone;

    /**
     * 密码
     */
    private String verificationCode;

    public PhoneMessageCodeAuthentication(String receipt, String phone, String verificationCode) {
        this(null, receipt, phone, verificationCode);
    }

    public PhoneMessageCodeAuthentication(Collection<? extends GrantedAuthority> authorities, String receipt, String phone, String verificationCode) {
        super(authorities, false);
        this.receipt = receipt;
        this.phone = phone;
        this.verificationCode = verificationCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return phone;
    }

}
