package com.ezcoding.common.sdk.core;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-30 15:12
 */
public class UserLoginRequestDTO implements Serializable {

    private static final long serialVersionUID = -3254727328806669918L;

    private static final String ACCOUNT_KEY = "account";
    private static final String PASSWORD_KEY = "password";
    private static final String LOGIN_TYPE_KEY = "login_type";
    private static final String VERIFICATION_CODE_KEY = "verification_code";
    private static final String RECEIPT_KEY = "receipt";
    private static final String PHONE_KEY = "phone";
    private static final String RESPONSE_TYPE_KEY = "response_type";
    private static final String CLIENT_ID_KEY = "client_id";
    private static final String REDIRECT_URI_KEY = "redirect_uri";
    private static final String SCOPE_KEY = "scope";
    private static final String STATE_KEY = "state";
    private static final String DEVICE_TYPE_KEY = "device_type";
    private static final String FAILURE_ACTION_KEY = "failure_action";
    private static final String FAILURE_URI_KEY = "failure_uri";

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

    public static UserLoginRequestDTO createByAccountPassword(String account, String password, DeviceTypeEnum deviceType) {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(account);
        userLoginRequestDTO.setPassword(password);
        userLoginRequestDTO.setLoginType(LoginRegisterTypeEnum.ACCOUNT_PASSWORD.getId());
        userLoginRequestDTO.setResponseType("token");
        userLoginRequestDTO.setClientId("testClient");
        userLoginRequestDTO.setScope("app");
        userLoginRequestDTO.setDeviceType((deviceType == null ? DeviceTypeEnum.UNKNOWN : deviceType).getId());
        userLoginRequestDTO.setFailureAction("response");
    }

}
