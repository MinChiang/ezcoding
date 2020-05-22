package com.ezcoding.common.foundation.core.tools.verification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

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
    private String code;

    /**
     * 验证码字节流
     */
    @JsonIgnore
    private transient byte[] data;

    public VerificationCode() {
    }

    public VerificationCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * 校验验证码
     *
     * @param code 用户输入的验证码
     * @return 验证码是否正确
     */
    public boolean verify(String code) {
        return StringUtils.equals(this.code, code);
    }

    /**
     * 校验验证码（忽略大小写）
     *
     * @param code 用户输入的验证码
     * @return 验证码是否正确
     */
    public boolean verifyIgnoreCase(String code) {
        return StringUtils.equalsIgnoreCase(this.code, code);
    }

}
