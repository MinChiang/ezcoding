package com.ezcoding.extend.spring.security.provider;

import com.ezcoding.common.core.user.IUserIdentifiable;
import com.ezcoding.common.core.user.model.UserIdentification;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.extend.spring.security.authentication.AccountPasswordAuthentication;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.core.authentication.ICustomUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_PARAM_VALIDATE_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_PASSWORD_INCORRECT_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 23:05
 */
public class AccountPasswordAuthenticationProvider extends AbstractLoginTypeAuthenticationProvider<AccountPasswordAuthentication> {

    protected PasswordEncoder passwordEncoder;

    public AccountPasswordAuthenticationProvider(ICustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super(userDetailsService);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    void postCheck(AccountPasswordAuthentication accountPasswordAuthentication, User user) {
        AssertUtils.mustTrue(
                passwordEncoder.matches(accountPasswordAuthentication.getPassword(), user.getPassword()),
                () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_PASSWORD_INCORRECT_ERROR).build()
        );
    }

    @Override
    IUserIdentifiable createIdentification(AccountPasswordAuthentication authentication) {
        UserIdentification userIdentification = new UserIdentification();
        userIdentification.setAccount(authentication.getAccount());
        return userIdentification;
    }

    @Override
    void preCheck(AccountPasswordAuthentication accountPasswordAuthentication) {
        AssertUtils.mustFalse(
                StringUtils.isAnyBlank(accountPasswordAuthentication.getAccount(), accountPasswordAuthentication.getPassword()),
                () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());
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
