package com.ezcoding.common.user.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-27 15:09
 */
public enum LoginRegisterTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(0),

    /**
     * 账户+密码
     */
    ACCOUNT_PASSWORD(1),

    /**
     * 邮箱+密码
     */
    EMAIL_PASSWORD(2),

    /**
     * 手机+密码
     */
    PHONE_PASSWORD(3),

    /**
     * 手机+验证码
     */
    PHONE_MESSAGE_CODE(4),

    /**
     * 账户+密码+图形验证码
     */
    ACCOUNT_PASSWORD_VERIFICATION_CODE(5),

    /**
     * 注册临时登陆
     */
    REGISTER(6);

    @EnumValue
    @JsonValue
    private final int id;

    LoginRegisterTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @JsonCreator
    public static LoginRegisterTypeEnum from(int i) {
        return EnumMappableUtils.map(i, LoginRegisterTypeEnum.class);
    }

}
