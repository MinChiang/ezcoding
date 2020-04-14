package com.ezcoding.extend.spring.security.provider;

import com.ezcoding.common.core.user.IUserIdentifiable;
import com.ezcoding.common.core.user.model.UserIdentification;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.extend.spring.security.authentication.PhoneMessageCodeAuthentication;
import com.ezcoding.module.user.bean.model.CheckVerificationInfo;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.core.verification.RedisVerificationServiceImpl;
import org.apache.commons.lang3.StringUtils;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_PARAM_VALIDATE_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_MESSAGE_CODE_NOT_VALID_ERROR;

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
                () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo();
        checkVerificationInfo.setVerificationCode(phoneMessageCodeAuthentication.getVerificationCode());
        checkVerificationInfo.setReceipt(phoneMessageCodeAuthentication.getReceipt());
        checkVerificationInfo.setTag(phoneMessageCodeAuthentication.getPhone());
        AssertUtils.mustTrue(numberVerificationService.check(checkVerificationInfo), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_MESSAGE_CODE_NOT_VALID_ERROR).build());
    }

    @Override
    void postCheck(PhoneMessageCodeAuthentication loginTypeAuthentication, User user) {

    }

    @Override
    IUserIdentifiable createIdentification(PhoneMessageCodeAuthentication authentication) {
        UserIdentification userIdentification = new UserIdentification();
        userIdentification.setPhone(authentication.getPhone());
        return userIdentification;
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
