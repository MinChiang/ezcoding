package com.ezcoding.module.user.bean.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-23 17:58
 */
public enum GrantTypeEnum {

    /**
     * 客户端模式
     */
    CLIENT_CREDENTIALS(0),

    /**
     * 密码模式
     */
    PASSWORD(1),

    /**
     * 授权码模式
     */
    AUTHORIZATION_CODE(2),

    /**
     * 令牌刷新
     */
    REFRESH_TOKEN(3),

    /**
     * 简化模式
     */
    IMPLICIT(4);

    private static final Map<Integer, GrantTypeEnum> ALL = new HashMap<Integer, GrantTypeEnum>();

    static {
        for (Field field : GrantTypeEnum.class.getDeclaredFields()) {
            if (field.getType().equals(GrantTypeEnum.class)) {
                try {
                    GrantTypeEnum instance = (GrantTypeEnum) field.get(null);
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
    public static GrantTypeEnum from(int id) {
        return ALL.get(id);
    }

    /**
     * 标志
     */
    @EnumValue
    @JsonValue
    private final int id;

    GrantTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
