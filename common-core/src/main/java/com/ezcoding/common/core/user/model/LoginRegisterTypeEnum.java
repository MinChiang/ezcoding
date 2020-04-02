package com.ezcoding.common.core.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Integer, LoginRegisterTypeEnum> ALL = new HashMap<Integer, LoginRegisterTypeEnum>();

    static {
        for (Field field : LoginRegisterTypeEnum.class.getDeclaredFields()) {
            if (field.getType().equals(LoginRegisterTypeEnum.class)) {
                try {
                    LoginRegisterTypeEnum instance = (LoginRegisterTypeEnum) field.get(null);
                    ALL.put(instance.id, instance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 转换
     *
     * @param id id
     * @return 对应类别
     */
    @JsonCreator
    public static LoginRegisterTypeEnum from(int id) {
        return ALL.get(id);
    }

    @JsonValue
    private final int id;

    LoginRegisterTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
