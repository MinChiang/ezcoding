package com.ezcoding.config;

import com.ezcoding.common.foundation.core.tools.verification.NumberVerificationCodeGenerator;
import com.ezcoding.common.foundation.core.tools.verification.OriginalVerificationCodeGenerator;
import com.ezcoding.extend.user.LocalUserProxy;
import com.ezcoding.module.user.service.IUserService;
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

    @Value("${verification.length}")
    private int defaultVerficationCodeLength;
    @Value("${verification.width}")
    private int defaultVerficationCodeWidth;
    @Value("${verification.height}")
    private int defaultVerficationCodeHeight;

    @Bean
    public OriginalVerificationCodeGenerator originalVerificationCodeGenerator() {
        OriginalVerificationCodeGenerator originalVerificationCodeGenerator = new OriginalVerificationCodeGenerator();
        originalVerificationCodeGenerator.setLength(defaultVerficationCodeLength);
        originalVerificationCodeGenerator.setHeight(defaultVerficationCodeHeight);
        originalVerificationCodeGenerator.setWidth(defaultVerficationCodeWidth);
        return originalVerificationCodeGenerator;
    }

    @Bean
    public NumberVerificationCodeGenerator verificationCodeGenerator() {
        NumberVerificationCodeGenerator numberVerificationCodeGenerator = new NumberVerificationCodeGenerator();
        numberVerificationCodeGenerator.setLength(defaultVerficationCodeLength);
        return numberVerificationCodeGenerator;
    }

}
