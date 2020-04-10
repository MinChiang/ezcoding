package com.ezcoding.extend.spring.security.provider;

import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.extend.spring.security.authentication.AccountPasswordVerificationAuthentication;
import com.ezcoding.module.user.bean.model.CheckVerificationInfo;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.core.verification.RedisVerificationServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_PARAM_VALIDATE_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_MESSAGE_CODE_NOT_VALID_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_PASSWORD_INCORRECT_ERROR;

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
                () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_PASSWORD_INCORRECT_ERROR).build()
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
                () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo();
        checkVerificationInfo.setTag(accountPasswordVerificationAuthentication.getAccount());
        checkVerificationInfo.setReceipt(accountPasswordVerificationAuthentication.getReceipt());
        checkVerificationInfo.setVerificationCode(accountPasswordVerificationAuthentication.getVerificationCode());
        AssertUtils.mustTrue(imageVerificationService.check(checkVerificationInfo), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_MESSAGE_CODE_NOT_VALID_ERROR).build());
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
