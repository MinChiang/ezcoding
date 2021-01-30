package com.ezcoding.common.sdk.core;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-30 15:12
 */
public class LoginRequestDTO implements Serializable {

    private static final long serialVersionUID = -3254727328806669918L;

    private static final String LOGIN_TYPE_KEY = "login_type";
    private static final String ACCOUNT_KEY = "account";
    private static final String PASSWORD_KEY = "password";
    private static final String PHONE_KEY = "phone";
    private static final String RECEIPT_KEY = "receipt";
    private static final String VERIFICATION_CODE_KEY = "verification_code";

    private String account;
    private String password;
    private String phone;
    private String receipt;
    private String verificationCode;
    private String loginType;

    private String clientId;
    private String redirectUri;
    private String scope;
    private String state;
    private Integer deviceType;
    private String failureAction;

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

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

}
