package com.ezcoding.module.user.core.authentication;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.extend.spring.security.authentication.AccountPasswordAuthentication;
import com.ezcoding.module.user.bean.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_PARAM_VALIDATE_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_EXIST_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-01 20:19
 */
public class AccountPasswordAuthenticationServiceImpl extends AbstractAuthenticationService {

    private PasswordEncoder passwordEncoder;

    @Override
    public AbstractLoginInfoPreservableAuthentication createAuthentication(Map<String, ?> context) {
        String account = (String) context.get(ACCOUNT_KEY);
        String password = (String) context.get(PASSWORD_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(account, password), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());
        return new AccountPasswordAuthentication(account, password);
    }

    @Override
    public User checkAndCreateUser(Map<String, ?> context) {
        String account = (String) context.get(ACCOUNT_KEY);
        String password = (String) context.get(PASSWORD_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(account, password), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());

        AssertUtils.mustNull(userMapper.selectOne(Wrappers.query(User.create().account(account))), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_EXIST_ERROR).build());
        return User.create().account(account).password(passwordEncoder.encode(password));
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
