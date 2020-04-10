package com.ezcoding.module.user.bean.model;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-11 10:06
 */
public class CheckVerificationInfo {

    /**
     * 回执
     */
    private String receipt;

    /**
     * 用户唯一标志
     */
    private String tag;

    /**
     * 验证码
     */
    private String verificationCode;

    public CheckVerificationInfo() {
    }

    public CheckVerificationInfo(String receipt, String tag, String verificationCode) {
        this.receipt = receipt;
        this.tag = tag;
        this.verificationCode = verificationCode;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

}
