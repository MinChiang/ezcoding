package com.ezcoding.account.extend.spring.security.provider;

import com.ezcoding.account.extend.spring.security.authentication.AccountPasswordVerificationAuthentication;
import com.ezcoding.account.module.user.bean.model.CheckVerificationInfo;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.core.verification.RedisVerificationServiceImpl;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-21 21:08
 */
public class AccountPasswordVerificationAuthenticationProvider extends AbstractLoginTypeAuthenticationProvider<AccountPasswordVerificationAuthentication> {

    private RedisVerificationServiceImpl imageVerificationService;
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> authentication) {
        return AccountPasswordVerificationAuthentication.class.isAssignableFrom(authentication);
    }

    public RedisVerificationServiceImpl getImageVerificationService() {
        return imageVerificationService;
    }

    public void setImageVerificationService(RedisVerificationServiceImpl imageVerificationService) {
        this.imageVerificationService = imageVerificationService;
    }

    @Override
    void postCheck(AccountPasswordVerificationAuthentication accountPasswordVerificationAuthentication, User user) {
        AssertUtils.mustTrue(
                passwordEncoder.matches(accountPasswordVerificationAuthentication.getPassword(), user.getPassword()),
                UserExceptionConstants.USER_PASSWORD_INCORRECT_ERROR
        );
    }

    @Override
    User createUserExample(AccountPasswordVerificationAuthentication authentication) {
        return User.create().account(authentication.getAccount());
    }

    @Override
    void preCheck(AccountPasswordVerificationAuthentication accountPasswordVerificationAuthentication) {
        AssertUtils.mustFalse(
                StringUtils.isAnyBlank(accountPasswordVerificationAuthentication.getAccount(),
                        accountPasswordVerificationAuthentication.getPassword(),
                        accountPasswordVerificationAuthentication.getReceipt(),
                        accountPasswordVerificationAuthentication.getVerificationCode()),
                CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("账号、密码、回执、验证码").build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo();
        checkVerificationInfo.setTag(accountPasswordVerificationAuthentication.getAccount());
        checkVerificationInfo.setReceipt(accountPasswordVerificationAuthentication.getReceipt());
        checkVerificationInfo.setVerificationCode(accountPasswordVerificationAuthentication.getVerificationCode());
        AssertUtils.mustTrue(imageVerificationService.check(checkVerificationInfo), UserExceptionConstants.USER_MESSAGE_CODE_NOT_VALID_ERROR);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
