package com.ezcoding.common.core.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-16 21:48
 */
public enum UserStatusEnum {

    /**
     * 正常
     */
    NORMAL(0),

    /**
     * 已锁定
     */
    LOCKED(1),

    /**
     * 已注销
     */
    CANCELED(2);

    private static final Map<Integer, UserStatusEnum> ALL = new HashMap<Integer, UserStatusEnum>();

    static {
        for (Field field : UserStatusEnum.class.getDeclaredFields()) {
            if (field.getType().equals(UserStatusEnum.class)) {
                try {
                    UserStatusEnum instance = (UserStatusEnum) field.get(null);
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
    public static UserStatusEnum from(int id) {
        return ALL.get(id);
    }

    @JsonValue
    private final int id;

    UserStatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
