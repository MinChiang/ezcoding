package com.ezcoding.extend.spring.security.provider;

import com.ezcoding.common.core.user.IUserIdentifiable;
import com.ezcoding.common.core.user.model.UserIdentification;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.extend.spring.security.authentication.PhonePasswordAuthentication;
import com.ezcoding.module.user.bean.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_PARAM_VALIDATE_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_PASSWORD_INCORRECT_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-03 22:41
 */
public class PhonePasswordAuthenticationProvider extends AbstractLoginTypeAuthenticationProvider<PhonePasswordAuthentication> {

    private PasswordEncoder passwordEncoder;

    @Override
    protected void preCheck(PhonePasswordAuthentication authentication) {
        AssertUtils.mustFalse(
                StringUtils.isAnyBlank(authentication.getPhone(), authentication.getPassword()),
                () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build()
        );
    }

    @Override
    protected void postCheck(PhonePasswordAuthentication authentication, User user) throws AuthenticationException {
        AssertUtils.mustTrue(
                authentication.getPhone().equalsIgnoreCase(user.getPhone()) &&
                        passwordEncoder.matches(authentication.getPassword(), user.getPassword()),
                () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_PASSWORD_INCORRECT_ERROR).build()

        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhonePasswordAuthentication.class.isAssignableFrom(authentication);
    }

    @Override
    IUserIdentifiable createIdentification(PhonePasswordAuthentication authentication) {
        UserIdentification userIdentification = new UserIdentification();
        userIdentification.setPhone(authentication.getPhone());
        return userIdentification;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
