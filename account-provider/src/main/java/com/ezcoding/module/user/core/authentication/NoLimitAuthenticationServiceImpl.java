package com.ezcoding.module.user.core.authentication;

import com.ezcoding.common.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.extend.spring.security.authentication.NoLimitAuthentication;
import com.ezcoding.module.user.bean.model.User;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 10:39
 */
public class NoLimitAuthenticationServiceImpl extends AbstractAuthenticationService {

    public NoLimitAuthenticationServiceImpl(AuthenticationManager authenticationManager, IBasicUserService basicUserService) {
        super(authenticationManager, basicUserService);
    }

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
