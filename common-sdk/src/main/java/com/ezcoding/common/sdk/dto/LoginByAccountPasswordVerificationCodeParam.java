package com.ezcoding.common.sdk.dto;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-08 16:37
 */
public class LoginByAccountPasswordVerificationCodeParam extends AbstractLoginParam implements Serializable {

    private static final long serialVersionUID = 3590942842665435587L;

    private String account;
    private String password;
    private String verificationCode;
    private String receipt;

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

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

}
