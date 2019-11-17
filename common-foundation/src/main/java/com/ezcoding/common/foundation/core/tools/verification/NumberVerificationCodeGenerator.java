package com.ezcoding.common.foundation.core.tools.verification;

import java.util.Random;

/**
 * @author i_Mjunqi
 * @version 1.0.0
 * @date 2018-11-21 00:12
 */
public class NumberVerificationCodeGenerator extends AbstractLimitLengthVerificationCodeGenerator {

    @Override
    public VerificationCode generate() {
        // 生成6位随机数(纯数字)
        Random random = new Random();
        StringBuilder verifyCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            verifyCode.append(random.nextInt(10));
        }

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(verifyCode.toString());
        return verificationCode;
    }

}
