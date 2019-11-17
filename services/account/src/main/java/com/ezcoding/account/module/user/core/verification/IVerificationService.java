package com.ezcoding.account.module.user.core.verification;

import com.ezcoding.account.module.user.bean.model.CheckVerificationInfo;
import com.ezcoding.account.module.user.bean.model.VerificationInfo;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-11 9:58
 */
public interface IVerificationService {

    /**
     * 生成验证码
     *
     * @param tag 用户唯一标志
     * @return 生成的验证码
     */
    VerificationInfo generate(String tag);

    /**
     * 校验验证码
     *
     * @param checkVerificationInfo 输入的验证码信息
     * @return 验证码是否正确
     */
    boolean check(CheckVerificationInfo checkVerificationInfo);

    /**
     * 根据回执获取验证码信息
     *
     * @param receipt 回执
     * @return 验证码信息
     */
    VerificationInfo get(String receipt);

    /**
     * 根据回执删除验证码
     *
     * @param receipt 回执
     * @return 删除的验证码信息
     */
    VerificationInfo delete(String receipt);

}
