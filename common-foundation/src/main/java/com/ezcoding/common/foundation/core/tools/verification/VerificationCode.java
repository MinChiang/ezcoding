package com.ezcoding.common.foundation.core.tools.verification;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class VerificationCode implements Serializable {

    private static final long serialVersionUID = 43433697265003439L;

    /**
     * 真实的验证码
     */
    private final char[] code;

    /**
     * 验证码字节流
     */
    @JsonIgnore
    private final transient byte[] data;

    public VerificationCode(char[] code, byte[] data) {
        this.code = code;
        this.data = data;
    }

    public char[] getCode() {
        return code;
    }

    public byte[] getData() {
        return data;
    }

    /**
     * 校验验证码
     *
     * @param code 用户输入的验证码
     * @return 验证码是否正确
     */
    public boolean verify(char[] code) {
        return code != null && Arrays.equals(code, this.code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VerificationCode that = (VerificationCode) o;
        return Arrays.equals(code, that.code) &&
                Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(code);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

}
