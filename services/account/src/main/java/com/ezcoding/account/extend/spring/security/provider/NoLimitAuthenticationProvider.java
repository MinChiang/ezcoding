package com.ezcoding.account.extend.spring.security.provider;

import com.ezcoding.account.extend.spring.security.authentication.NoLimitAuthentication;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 9:57
 */
public class NoLimitAuthenticationProvider extends AbstractLoginTypeAuthenticationProvider<NoLimitAuthentication> {

    @Override
    void postCheck(NoLimitAuthentication loginTypeAuthentication, User user) {

    }

    @Override
    User createUserExample(NoLimitAuthentication authentication) {
        return User.create().code(authentication.getPrincipal());
    }

    @Override
    void preCheck(NoLimitAuthentication noLimitAuthentication) {
        AssertUtils.mustFalse(
                StringUtils.isBlank(noLimitAuthentication.getCode()),
                CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("用户唯一编号").build());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return NoLimitAuthentication.class.isAssignableFrom(authentication);
    }

}
