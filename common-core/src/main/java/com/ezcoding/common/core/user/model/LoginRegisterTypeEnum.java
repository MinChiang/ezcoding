package com.ezcoding.common.core.user.model;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static final Map<Integer, LoginRegisterTypeEnum> ALL = Arrays.stream(LoginRegisterTypeEnum.class.getEnumConstants()).collect(Collectors.toMap(value -> value.id, value -> value));

    /**
     * 转换
     *
     * @param id id
     * @return 对应类别
     */
    public static LoginRegisterTypeEnum from(int id) {
        return ALL.get(id);
    }

    public final int id;

    LoginRegisterTypeEnum(int id) {
        this.id = id;
    }

}
