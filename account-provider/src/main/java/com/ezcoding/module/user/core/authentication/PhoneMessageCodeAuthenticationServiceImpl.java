package com.ezcoding.module.user.core.authentication;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.extend.spring.security.authentication.PhoneMessageCodeAuthentication;
import com.ezcoding.module.user.bean.model.CheckVerificationInfo;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.core.verification.RedisVerificationServiceImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_PARAM_VALIDATE_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_EXIST_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_MESSAGE_CODE_NOT_VALID_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-01 20:19
 */
public class PhoneMessageCodeAuthenticationServiceImpl extends AbstractAuthenticationService {

    private RedisVerificationServiceImpl numberVerificationService;

    @Override
    public AbstractLoginInfoPreservableAuthentication createAuthentication(Map<String, ?> context) {
        String receipt = (String) context.get(RECEIPT_KEY);
        String phone = (String) context.get(PHONE_KEY);
        String verificationCode = (String) context.get(VERIFICATION_CODE_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(receipt, phone, verificationCode), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo(receipt, phone, verificationCode);
        AssertUtils.mustTrue(numberVerificationService.check(checkVerificationInfo), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_MESSAGE_CODE_NOT_VALID_ERROR).build());

        return new PhoneMessageCodeAuthentication(receipt, phone, verificationCode);
    }

    @Override
    public User checkAndCreateUser(Map<String, ?> context) {
        String receipt = (String) context.get(RECEIPT_KEY);
        String phone = (String) context.get(PHONE_KEY);
        String verificationCode = (String) context.get(VERIFICATION_CODE_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(receipt, phone, verificationCode), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo(receipt, phone, verificationCode);
        AssertUtils.mustTrue(numberVerificationService.check(checkVerificationInfo), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_MESSAGE_CODE_NOT_VALID_ERROR).build());
        AssertUtils.mustNull(userMapper.selectOne(Wrappers.query(User.create().phone(phone))), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_EXIST_ERROR).build());
        return User.create().phone(phone);
    }

    public RedisVerificationServiceImpl getNumberVerificationService() {
        return numberVerificationService;
    }

    public void setNumberVerificationService(RedisVerificationServiceImpl numberVerificationService) {
        this.numberVerificationService = numberVerificationService;
    }

}
