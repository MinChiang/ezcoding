package com.ezcoding.account.module.user.core.authentication;

import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.base.web.extend.spring.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.sdk.account.user.bean.model.LoginRegisterTypeEnum;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-01 16:44
 */
public class CompositeAuthenticationServiceImpl implements IAuthenticationService {

    private Map<LoginRegisterTypeEnum, IAuthenticationService> loginDelegate = Maps.newHashMap();
    private Map<LoginRegisterTypeEnum, IAuthenticationService> registerDelegate = Maps.newHashMap();

    /**
     * 注册对应的登陆服务
     *
     * @param loginRegisterTypeEnum 登陆注册类型
     */
    public void loginService(LoginRegisterTypeEnum loginRegisterTypeEnum, IAuthenticationService authenticationService) {
        loginDelegate.put(loginRegisterTypeEnum, authenticationService);
    }

    /**
     * 注册对应的注册服务
     *
     * @param loginRegisterTypeEnum 登陆注册类型
     */
    public void registerService(LoginRegisterTypeEnum loginRegisterTypeEnum, IAuthenticationService authenticationService) {
        registerDelegate.put(loginRegisterTypeEnum, authenticationService);
    }

    @Override
    public AbstractLoginInfoPreservableAuthentication login(Map<String, ?> context) {
        LoginRegisterTypeEnum loginType = (LoginRegisterTypeEnum) context.get(LOGIN_TYPE_KEY);

        IAuthenticationService authenticationService = loginDelegate.get(loginType);
        AssertUtils.mustNotNull(authenticationService, UserExceptionConstants.USER_AUTHENTICATION_UNKOWN_LOGIN_TYPE_ERROR);
        return authenticationService.login(context);
    }

    @Override
    public User register(Map<String, ?> context) {
        LoginRegisterTypeEnum registerType = (LoginRegisterTypeEnum) context.get(REGISTER_TYPE_KEY);

        IAuthenticationService authenticationService = registerDelegate.get(registerType);
        AssertUtils.mustNotNull(authenticationService, UserExceptionConstants.USER_AUTHENTICATION_UNKOWN_REGISTER_TYPE_ERROR);
        return authenticationService.register(context);
    }

    /**
     * 注册对应的注册钩子服务
     *
     * @param registerSuccessHandler 注册钩子服务
     */
    public void registerHandler(IRegisterSuccessHandler registerSuccessHandler) {
        for (Map.Entry<LoginRegisterTypeEnum, IAuthenticationService> entry : registerDelegate.entrySet()) {
            IAuthenticationService value = entry.getValue();
            if (value instanceof AbstractAuthenticationService) {
                ((AbstractAuthenticationService) value).registerHandler(registerSuccessHandler);
            }
        }
    }

}
