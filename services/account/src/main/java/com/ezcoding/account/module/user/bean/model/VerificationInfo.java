package com.ezcoding.account.module.user.bean.model;

import com.ezcoding.common.foundation.core.tools.verification.VerificationCode;
import lombok.Data;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-11 0:06
 */
@Data
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

}
