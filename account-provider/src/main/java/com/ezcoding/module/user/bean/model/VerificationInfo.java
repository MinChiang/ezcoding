package com.ezcoding.module.user.bean.model;

import com.ezcoding.common.foundation.core.tools.verification.VerificationCode;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-11 0:06
 */
public class VerificationInfo {

    /**
     * 验证码信息
     */
    private VerificationCode verificationCode;

    /**
     * 用户标志信息（account，手机号码，email等）
     */
    private String tag;

    /**
     * 回执
     */
    private String receipt;

    public VerificationCode getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(VerificationCode verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

}
