package com.ezcoding.common.sdk.dto;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-08 16:36
 */
public class LoginByPhoneMessageCodeParam extends AbstractLoginParam implements Serializable {

    private static final long serialVersionUID = -1879200284469324792L;

    private String phone;
    private String verificationCode;
    private String receipt;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
