package com.ezcoding.account.module.user.core.authentication;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ezcoding.base.web.extend.spring.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.account.extend.spring.security.authentication.PhonePasswordAuthentication;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-01 20:19
 */
public class PhonePasswordAuthenticationServiceImpl extends AbstractAuthenticationService {

    private PasswordEncoder passwordEncoder;

    @Override
    public AbstractLoginInfoPreservableAuthentication createAuthentication(Map<String, ?> context) {
        String phone = (String) context.get(PHONE_KEY);
        String password = (String) context.get(PASSWORD_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(phone, password), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("手机号、密码").build());
        return new PhonePasswordAuthentication(phone, password);
    }

    @Override
    public User checkAndCreateUser(Map<String, ?> context) {
        String phone = (String) context.get(PHONE_KEY);
        String password = (String) context.get(PASSWORD_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(phone, password), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("手机号、密码").build());

        AssertUtils.mustNull(userMapper.selectOne(Wrappers.query(User.create().phone(phone))), UserExceptionConstants.USER_EXIST_ERROR);
        return User.create().phone(phone).password(passwordEncoder.encode(password));
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
