package com.ezcoding.account.extend.spring.security.provider;

import com.ezcoding.account.extend.spring.security.authentication.PhonePasswordAuthentication;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("手机号、密码").build()
        );
    }

    @Override
    protected void postCheck(PhonePasswordAuthentication authentication, User user) throws AuthenticationException {
        AssertUtils.mustTrue(
                authentication.getPhone().equalsIgnoreCase(user.getPhone()) &&
                        passwordEncoder.matches(authentication.getPassword(), user.getPassword()),
                UserExceptionConstants.USER_PASSWORD_INCORRECT_ERROR
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhonePasswordAuthentication.class.isAssignableFrom(authentication);
    }

    @Override
    User createUserExample(PhonePasswordAuthentication authentication) {
        return User.create().phone(authentication.getPhone());
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
