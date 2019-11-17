package com.ezcoding.sdk.account.user.bean.dto;

import com.ezcoding.sdk.account.user.bean.model.DeviceTypeEnum;
import com.ezcoding.sdk.account.user.bean.model.LoginRegisterTypeEnum;
import lombok.Data;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-16 21:48
 */
@Data
public class UserLoginDTO {

    /**
     * 用户账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户输入验证码
     */
    private String verificationCode;

    /**
     * 记住我
     */
    private Boolean rememberMe;

    /**
     * 设备类型
     */
    private DeviceTypeEnum deviceType;

    /**
     * 登陆Ip
     */
    private String ip;

    /**
     * 短信验证码
     */
    private String messageCode;

    /**
     * 登陆类型
     */
    private LoginRegisterTypeEnum loginType;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 验证码回执
     */
    private String receipt;

}
