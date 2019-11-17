package com.ezcoding.account.module.user.bean.model;

import lombok.Data;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-11 10:06
 */
@Data
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

}
