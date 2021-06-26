package com.ezcoding.common.sdk.dto;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-30 15:12
 */
public class UserLoginRequestParam implements Serializable {

    private static final long serialVersionUID = -3254727328806669918L;

    private String account;
    private String password;
    private Integer loginType;
    private String verificationCode;
    private String receipt;
    private String phone;
    private String responseType;
    private String clientId;
    private String redirectUri;
    private String scope;
    private String state;
    private Integer deviceType;
    private String failureAction;
    private String failureUri;

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

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getScope() {
        return scope;
    }

    public String getState() {
        return state;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public String getFailureAction() {
        return failureAction;
    }

    public String getFailureUri() {
        return failureUri;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public void setFailureAction(String failureAction) {
        this.failureAction = failureAction;
    }

    public void setFailureUri(String failureUri) {
        this.failureUri = failureUri;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public static UserLoginRequestParam createByAccountPassword(String account, String password, DeviceTypeEnum deviceType) {
        UserLoginRequestParam userLoginRequestParam = new UserLoginRequestParam();
        userLoginRequestParam.setAccount(account);
        userLoginRequestParam.setPassword(password);
        userLoginRequestParam.setLoginType(LoginRegisterTypeEnum.ACCOUNT_PASSWORD.id);
        userLoginRequestParam.setResponseType("token");
        userLoginRequestParam.setClientId("testClient");
        userLoginRequestParam.setScope("app");
        userLoginRequestParam.setDeviceType((deviceType == null ? DeviceTypeEnum.UNKNOWN : deviceType).id);
        userLoginRequestParam.setFailureAction("response");
        return userLoginRequestParam;
    }

    @Override
    public String toString() {
        return "UserLoginRequestDTO{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", loginType=" + loginType +
                ", verificationCode='" + verificationCode + '\'' +
                ", receipt='" + receipt + '\'' +
                ", phone='" + phone + '\'' +
                ", responseType='" + responseType + '\'' +
                ", clientId='" + clientId + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", scope='" + scope + '\'' +
                ", state='" + state + '\'' +
                ", deviceType=" + deviceType +
                ", failureAction='" + failureAction + '\'' +
                ", failureUri='" + failureUri + '\'' +
                '}';
    }

}
