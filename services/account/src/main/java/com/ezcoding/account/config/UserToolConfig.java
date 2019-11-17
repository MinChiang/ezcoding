package com.ezcoding.account.config;

import com.ezcoding.common.foundation.core.tools.verification.KapatchaImageVerificationCodeGenerator;
import com.ezcoding.common.foundation.core.tools.verification.NumberVerificationCodeGenerator;
import com.ezcoding.common.foundation.core.tools.verification.OriginalVerificationCodeGenerator;
import com.ezcoding.sdk.account.user.constant.AccountUserConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-28 9:33
 */
@Configuration
public class UserToolConfig {

    @Value("${token.header}")
    private String header;
    @Value("${token.expiration}")
    private long expiration;
    @Value("${token.issuer}")
    private String issuer;
    @Value("${token.subject}")
    private String subject;

    @Value("${VERIFICATION_CODE_WIDTH}")
    private int defaultVerficationCodeWidth;
    @Value("${VERIFICATION_CODE_HEIGHT}")
    private int defaultVerficationCodeHeight;
    @Value("${VERIFICATION_CODE_LENGTH}")
    private int defaultVerficationCodeLength;

    @Bean
    public OriginalVerificationCodeGenerator originalVerificationCodeGenerator() {
        OriginalVerificationCodeGenerator originalVerificationCodeGenerator = new OriginalVerificationCodeGenerator();
        originalVerificationCodeGenerator.setLength(defaultVerficationCodeLength);
        originalVerificationCodeGenerator.setHeigth(defaultVerficationCodeHeight);
        originalVerificationCodeGenerator.setWidth(defaultVerficationCodeWidth);
        return originalVerificationCodeGenerator;
    }

    @Bean
    public NumberVerificationCodeGenerator verificationCodeGenerator() {
        NumberVerificationCodeGenerator numberVerificationCodeGenerator = new NumberVerificationCodeGenerator();
        numberVerificationCodeGenerator.setLength(AccountUserConstants.USER_REGISTER_VERIFICATION_CODE_LENGTH);
        return numberVerificationCodeGenerator;
    }

    @Bean
    public KapatchaImageVerificationCodeGenerator kapatchaImageVerificationCodeGenerator() {
        KapatchaImageVerificationCodeGenerator kapatchaImageVerificationCodeGenerator = new KapatchaImageVerificationCodeGenerator();
        kapatchaImageVerificationCodeGenerator.setLength(defaultVerficationCodeLength);
        kapatchaImageVerificationCodeGenerator.setHeigth(defaultVerficationCodeHeight);
        kapatchaImageVerificationCodeGenerator.setWidth(defaultVerficationCodeWidth);
        return kapatchaImageVerificationCodeGenerator;
    }

}
