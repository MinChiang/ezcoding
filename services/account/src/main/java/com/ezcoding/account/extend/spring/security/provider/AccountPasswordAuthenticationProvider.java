package com.ezcoding.account.extend.spring.security.provider;

import com.ezcoding.account.extend.spring.security.authentication.AccountPasswordAuthentication;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 23:05
 */
public class AccountPasswordAuthenticationProvider extends AbstractLoginTypeAuthenticationProvider<AccountPasswordAuthentication> {

    private PasswordEncoder passwordEncoder;

    @Override
    void postCheck(AccountPasswordAuthentication accountPasswordAuthentication, User user) {
        AssertUtils.mustTrue(
                passwordEncoder.matches(accountPasswordAuthentication.getPassword(), user.getPassword()),
                UserExceptionConstants.USER_PASSWORD_INCORRECT_ERROR
        );
    }

    @Override
    User createUserExample(AccountPasswordAuthentication authentication) {
        return User.create().account(authentication.getAccount());
    }

    @Override
    void preCheck(AccountPasswordAuthentication accountPasswordAuthentication) {
        AssertUtils.mustFalse(
                StringUtils.isAnyBlank(accountPasswordAuthentication.getAccount(), accountPasswordAuthentication.getPassword()),
                CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("账号、密码").build());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccountPasswordAuthentication.class.isAssignableFrom(authentication);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
