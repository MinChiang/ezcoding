package com.ezcoding.account.module.user.core.authentication;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ezcoding.account.extend.spring.security.authentication.AccountPasswordVerificationAuthentication;
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
 * @date 2019-04-01 20:26
 */
public class AccountPasswordVerificationAuthenticationServiceImpl extends AbstractAuthenticationService {

    private RedisVerificationServiceImpl imageVerificationService;

    @Override
    public AbstractLoginInfoPreservableAuthentication createAuthentication(Map<String, ?> context) {
        String account = (String) context.get(ACCOUNT_KEY);
        String password = (String) context.get(PASSWORD_KEY);
        String receipt = (String) context.get(RECEIPT_KEY);
        String verificationCode = (String) context.get(VERIFICATION_CODE_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(account, password, receipt, verificationCode), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("账号、密码、回执、验证码").build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo(receipt, account, verificationCode);
        AssertUtils.mustTrue(imageVerificationService.check(checkVerificationInfo), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("验证码不正确").build());

        return new AccountPasswordVerificationAuthentication(account, password, verificationCode, receipt);
    }

    @Override
    public User checkAndCreateUser(Map<String, ?> context) {
        String account = (String) context.get(ACCOUNT_KEY);
        String password = (String) context.get(PASSWORD_KEY);
        String receipt = (String) context.get(RECEIPT_KEY);
        String verificationCode = (String) context.get(VERIFICATION_CODE_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(account, password, receipt, verificationCode), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("账号、密码、回执、验证码").build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo(receipt, account, verificationCode);
        AssertUtils.mustTrue(imageVerificationService.check(checkVerificationInfo), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("验证码不正确").build());
        AssertUtils.mustNull(userMapper.selectOne(Wrappers.query(User.create().account(account))), UserExceptionConstants.USER_EXIST_ERROR);
        return User.create().account(account).password(password);
    }

    public RedisVerificationServiceImpl getImageVerificationService() {
        return imageVerificationService;
    }

    public void setImageVerificationService(RedisVerificationServiceImpl imageVerificationService) {
        this.imageVerificationService = imageVerificationService;
    }

}
