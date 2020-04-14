package com.ezcoding.module.user.core.authentication;

import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.extend.spring.security.authentication.PhonePasswordAuthentication;
import com.ezcoding.module.user.bean.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_PARAM_VALIDATE_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_EXIST_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-01 20:19
 */
public class PhonePasswordAuthenticationServiceImpl extends AbstractAuthenticationService {

    private PasswordEncoder passwordEncoder;

    public PhonePasswordAuthenticationServiceImpl(AuthenticationManager authenticationManager, IBasicUserService basicUserService, PasswordEncoder passwordEncoder) {
        super(authenticationManager, basicUserService);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AbstractLoginInfoPreservableAuthentication createAuthentication(Map<String, ?> context) {
        String phone = (String) context.get(PHONE_KEY);
        String password = (String) context.get(PASSWORD_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(phone, password), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());
        return new PhonePasswordAuthentication(phone, password);
    }

    @Override
    public User checkAndCreateUser(Map<String, ?> context) {
        String phone = (String) context.get(PHONE_KEY);
        String password = (String) context.get(PASSWORD_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(phone, password), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());

        User user = User.create().phone(phone);
        AssertUtils.mustFalse(basicUserService.exist(user), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_EXIST_ERROR).build());
        return user.password(passwordEncoder.encode(password));
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
