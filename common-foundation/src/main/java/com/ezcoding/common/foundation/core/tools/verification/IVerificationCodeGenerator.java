package com.ezcoding.common.foundation.core.tools.verification;

import java.io.IOException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public interface IVerificationCodeGenerator {

    /**
     * 生成验证码
     *
     * @return 验证码
     * @throws IOException IO异常
     */
    VerificationCode generate() throws IOException;

}
