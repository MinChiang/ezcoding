package com.ezcoding.common.foundation.core.tools.verification;

import java.util.Random;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-21 00:12
 */
public class NumberVerificationCodeGenerator extends AbstractLimitLengthVerificationCodeGenerator {

    @Override
    public VerificationCode generate() {
        Random random = new Random();
        char[] cs = new char[length];
        for (int i = 0; i < length; i++) {
            cs[i] = (char) ('0' + random.nextInt(10));
        }
        return new VerificationCode(cs, null);
    }

}
