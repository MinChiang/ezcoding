package com.ezcoding.account.extend.spring.security.provider;

import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.account.module.user.bean.model.CheckVerificationInfo;
import com.ezcoding.account.extend.spring.security.authentication.PhoneMessageCodeAuthentication;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.account.module.user.core.verification.RedisVerificationServiceImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-04 10:44
 */
public class PhoneMessageCodeAuthenticationProvider extends AbstractLoginTypeAuthenticationProvider<PhoneMessageCodeAuthentication> {

    private RedisVerificationServiceImpl numberVerificationService;

    @Override
    protected void preCheck(PhoneMessageCodeAuthentication phoneMessageCodeAuthentication) {
        AssertUtils.mustFalse(
                StringUtils.isAnyBlank(phoneMessageCodeAuthentication.getReceipt(),
                        phoneMessageCodeAuthentication.getPhone(),
                        phoneMessageCodeAuthentication.getVerificationCode()),
                CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().params("回执、手机号、验证码").build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo();
        checkVerificationInfo.setVerificationCode(phoneMessageCodeAuthentication.getVerificationCode());
        checkVerificationInfo.setReceipt(phoneMessageCodeAuthentication.getReceipt());
        checkVerificationInfo.setTag(phoneMessageCodeAuthentication.getPhone());
        AssertUtils.mustTrue(numberVerificationService.check(checkVerificationInfo), UserExceptionConstants.USER_MESSAGE_CODE_NOT_VALID_ERROR);
    }

    @Override
    void postCheck(PhoneMessageCodeAuthentication loginTypeAuthentication, User user) {

    }

    @Override
    User createUserExample(PhoneMessageCodeAuthentication authentication) {
        return User.create().phone(authentication.getPhone());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneMessageCodeAuthentication.class.isAssignableFrom(authentication);
    }

    public RedisVerificationServiceImpl getNumberVerificationService() {
        return numberVerificationService;
    }

    public void setNumberVerificationService(RedisVerificationServiceImpl numberVerificationService) {
        this.numberVerificationService = numberVerificationService;
    }

}
