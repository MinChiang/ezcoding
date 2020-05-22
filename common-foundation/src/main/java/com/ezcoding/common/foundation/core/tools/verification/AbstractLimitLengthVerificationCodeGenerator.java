package com.ezcoding.common.foundation.core.tools.verification;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-05-22 17:16
 */
public abstract class AbstractLimitLengthVerificationCodeGenerator implements VerificationCodeGenerator {

    /**
     * 验证码位数
     */
    protected Integer length;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

}
