package com.ezcoding.account.module.user.core.authentication;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ezcoding.account.extend.spring.security.authentication.AccountPasswordAuthentication;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.base.web.extend.spring.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

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
        AssertUtils.mustFalse(StringUtils.isAnyBlank(account, password), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("账号、密码").build());
        return new AccountPasswordAuthentication(account, password);
    }

    @Override
    public User checkAndCreateUser(Map<String, ?> context) {
        String account = (String) context.get(ACCOUNT_KEY);
        String password = (String) context.get(PASSWORD_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(account, password), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("账号、密码").build());

        AssertUtils.mustNull(userMapper.selectOne(Wrappers.query(User.create().account(account))), UserExceptionConstants.USER_EXIST_ERROR);
        return User.create().account(account).password(passwordEncoder.encode(password));
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
