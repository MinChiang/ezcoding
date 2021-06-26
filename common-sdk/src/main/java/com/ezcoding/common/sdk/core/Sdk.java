package com.ezcoding.common.sdk.core;

import com.ezcoding.common.sdk.dto.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-04 14:42
 */
public interface Sdk extends TokenStorable {

    /**
     * 通过账号+密码登录
     *
     * @param param 参数
     * @return 登录后的token
     */
    Token loginByAccountPassword(LoginByAccountPasswordParam param);

    /**
     * 通过邮箱账号+密码登录
     *
     * @param param 参数
     * @return 登录后的token
     */
    Token loginByEmailPassword(LoginByEmailPasswordParam param);

    /**
     * 通过手机号+密码登录
     *
     * @param param 参数
     * @return 登录后的token
     */
    Token loginByPhonePassword(LoginByPhonePasswordParam param);

    /**
     * 通过手机号+验证码登录
     *
     * @param param 参数
     * @return 登录后的token
     */
    Token loginByPhoneMessageCode(LoginByPhoneMessageCodeParam param);

    /**
     * 通过账号+密码+验证码登录
     *
     * @param param 参数
     * @return 登录后的token
     */
    Token loginByAccountPasswordVerificationCode(LoginByAccountPasswordVerificationCodeParam param);

    /**
     * 给手机发送验证码并且接收返回凭证
     *
     * @param phone 手机号
     * @return 返回凭证
     */
    String sendMessageCodeReceiveReceipt(String phone);

    /**
     * 退出登录
     */
    void logout();

}
