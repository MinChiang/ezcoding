package com.ezcoding.extend.spring.security.provider;

import com.ezcoding.common.core.user.IUserIdentifiable;
import com.ezcoding.common.core.user.model.UserIdentification;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.extend.spring.security.authentication.NoLimitAuthentication;
import com.ezcoding.module.user.bean.model.User;
import org.apache.commons.lang3.StringUtils;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_PARAM_VALIDATE_ERROR;

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
    IUserIdentifiable createIdentification(NoLimitAuthentication authentication) {
        UserIdentification userIdentification = new UserIdentification();
        userIdentification.setCode(authentication.getCode());
        return userIdentification;
    }

    @Override
    void preCheck(NoLimitAuthentication noLimitAuthentication) {
        AssertUtils.mustFalse(
                StringUtils.isBlank(noLimitAuthentication.getCode()),
                () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return NoLimitAuthentication.class.isAssignableFrom(authentication);
    }

}
