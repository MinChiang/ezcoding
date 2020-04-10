package com.ezcoding.module.user.core.authentication;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.extend.spring.security.authentication.AccountPasswordVerificationAuthentication;
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
        AssertUtils.mustFalse(StringUtils.isAnyBlank(account, password, receipt, verificationCode), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo(receipt, account, verificationCode);
        AssertUtils.mustTrue(imageVerificationService.check(checkVerificationInfo), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());

        return new AccountPasswordVerificationAuthentication(account, password, verificationCode, receipt);
    }

    @Override
    public User checkAndCreateUser(Map<String, ?> context) {
        String account = (String) context.get(ACCOUNT_KEY);
        String password = (String) context.get(PASSWORD_KEY);
        String receipt = (String) context.get(RECEIPT_KEY);
        String verificationCode = (String) context.get(VERIFICATION_CODE_KEY);
        AssertUtils.mustFalse(StringUtils.isAnyBlank(account, password, receipt, verificationCode), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());

        CheckVerificationInfo checkVerificationInfo = new CheckVerificationInfo(receipt, account, verificationCode);
        AssertUtils.mustTrue(imageVerificationService.check(checkVerificationInfo), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_MESSAGE_CODE_NOT_VALID_ERROR).build());
        AssertUtils.mustNull(userMapper.selectOne(Wrappers.query(User.create().account(account))), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_EXIST_ERROR).build());
        return User.create().account(account).password(password);
    }

    public RedisVerificationServiceImpl getImageVerificationService() {
        return imageVerificationService;
    }

    public void setImageVerificationService(RedisVerificationServiceImpl imageVerificationService) {
        this.imageVerificationService = imageVerificationService;
    }

}
