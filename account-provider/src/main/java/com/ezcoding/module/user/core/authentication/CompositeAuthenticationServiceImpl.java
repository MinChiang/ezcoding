package com.ezcoding.module.user.core.authentication;

import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.module.user.bean.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_AUTHENTICATION_UNKOWN_LOGIN_TYPE_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_AUTHENTICATION_UNKOWN_REGISTER_TYPE_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-01 16:44
 */
public class CompositeAuthenticationServiceImpl implements IAuthenticationService {

    private Map<LoginRegisterTypeEnum, IAuthenticationService> loginDelegate = new HashMap<>();
    private Map<LoginRegisterTypeEnum, IAuthenticationService> registerDelegate = new HashMap<>();

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
        AssertUtils.mustNotNull(authenticationService, () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_AUTHENTICATION_UNKOWN_LOGIN_TYPE_ERROR).build());
        return authenticationService.login(context);
    }

    @Override
    public User register(Map<String, ?> context) {
        LoginRegisterTypeEnum registerType = (LoginRegisterTypeEnum) context.get(REGISTER_TYPE_KEY);

        IAuthenticationService authenticationService = registerDelegate.get(registerType);
        AssertUtils.mustNotNull(authenticationService, () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_AUTHENTICATION_UNKOWN_REGISTER_TYPE_ERROR).build());
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
