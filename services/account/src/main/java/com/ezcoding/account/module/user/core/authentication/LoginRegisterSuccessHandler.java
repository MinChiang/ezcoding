package com.ezcoding.account.module.user.core.authentication;

import com.ezcoding.sdk.account.user.bean.model.LoginRegisterTypeEnum;
import com.ezcoding.account.module.user.bean.model.User;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 9:43
 */
@Deprecated
public class LoginRegisterSuccessHandler implements IRegisterSuccessHandler {

    private CompositeAuthenticationServiceImpl compositeAuthenticationService;

    @Override
    public void onRegisterSuccess(Map<String, ?> context, User user) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(IAuthenticationService.CODE_KEY, user.getCode());
        map.put(IAuthenticationService.LOGIN_TYPE_KEY, LoginRegisterTypeEnum.REGISTER);
        compositeAuthenticationService.login(map);
    }

    public CompositeAuthenticationServiceImpl getCompositeAuthenticationService() {
        return compositeAuthenticationService;
    }

    public void setCompositeAuthenticationService(CompositeAuthenticationServiceImpl compositeAuthenticationService) {
        this.compositeAuthenticationService = compositeAuthenticationService;
    }
}
