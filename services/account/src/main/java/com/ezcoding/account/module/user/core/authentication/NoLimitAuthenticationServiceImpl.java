package com.ezcoding.account.module.user.core.authentication;

import com.ezcoding.account.extend.spring.security.authentication.NoLimitAuthentication;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.base.web.extend.spring.security.authentication.AbstractLoginInfoPreservableAuthentication;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 10:39
 */
public class NoLimitAuthenticationServiceImpl extends AbstractAuthenticationService {

    @Override
    public AbstractLoginInfoPreservableAuthentication createAuthentication(Map<String, ?> context) {
        String code = (String) context.get(CODE_KEY);
        return new NoLimitAuthentication(code);
    }

    @Override
    public User checkAndCreateUser(Map<String, ?> context) {
        throw new UnsupportedOperationException("本注册方式暂未实现");
    }

}
