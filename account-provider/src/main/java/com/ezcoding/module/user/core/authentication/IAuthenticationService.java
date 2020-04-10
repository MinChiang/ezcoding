package com.ezcoding.module.user.core.authentication;

import com.ezcoding.common.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.module.user.bean.model.User;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-16 11:27
 */
public interface IAuthenticationService {

    String LOGIN_TYPE_KEY = "loginType";
    String REGISTER_TYPE_KEY = "registerType";
    String CODE_KEY = "code";
    String ACCOUNT_KEY = "account";
    String PASSWORD_KEY = "password";
    String PHONE_KEY = "phone";
    String RECEIPT_KEY = "receipt";
    String VERIFICATION_CODE_KEY = "verificationCode";
    String DEVICE_TYPE = "deviceType";

    /**
     * 统一登陆接口
     *
     * @param context 登陆内容
     * @return 登陆凭证
     */
    AbstractLoginInfoPreservableAuthentication login(Map<String, ?> context);

    /**
     * 统一注册接口
     *
     * @param context 注册内容
     * @return 登陆凭证
     */
    User register(Map<String, ?> context);

}
