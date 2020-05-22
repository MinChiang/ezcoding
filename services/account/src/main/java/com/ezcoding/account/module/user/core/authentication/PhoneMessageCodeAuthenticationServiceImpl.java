package com.ezcoding.account.module.user.core.authentication;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ezcoding.account.extend.spring.security.authentication.PhoneMessageCodeAuthentication;
import com.ezcoding.account.module.user.bean.model.CheckVerificationInfo;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.core.verification.RedisVerificationServiceImpl;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.base.web.extend.spring.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

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
        AssertUtils.mustFalse(StringUtils.isAnyBlank(receipt, phone, verificationCode), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().params("回执、手机号、验证码").build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo(receipt, phone, verificationCode);
        AssertUtils.mustTrue(numberVerificationService.check(checkVerificationInfo), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("验证码不正确").build());

        return new PhoneMessageCodeAuthentication(receipt, phone, verificationCode);
    }

    @Override
    public User checkAndCreateUser(Map<String, ?> context) {
        String receipt = (String) context.get(RECEIPT_KEY);
        String phone = (String) context.get(PHONE_KEY);
        String verificationCode = (String) context.get(VERIFICATION_CODE_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(receipt, phone, verificationCode), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().params("回执、手机号、验证码").build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo(receipt, phone, verificationCode);
        AssertUtils.mustTrue(numberVerificationService.check(checkVerificationInfo), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("验证码不正确").build());
        AssertUtils.mustNull(userMapper.selectOne(Wrappers.query(User.create().phone(phone))), UserExceptionConstants.USER_EXIST_ERROR);
        return User.create().phone(phone);
    }

    public RedisVerificationServiceImpl getNumberVerificationService() {
        return numberVerificationService;
    }

    public void setNumberVerificationService(RedisVerificationServiceImpl numberVerificationService) {
        this.numberVerificationService = numberVerificationService;
    }

}
