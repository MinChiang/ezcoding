package com.ezcoding.common.core.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-04-18 16:15
 */
public enum GenderEnum {

    /**
     * 未知
     */
    UNKOWN(0),

    /**
     * 男
     */
    MALE(1),

    /**
     * 女
     */
    FEMALE(2);

    private static final Map<Integer, GenderEnum> ALL = new HashMap<Integer, GenderEnum>();

    static {
        for (Field field : GenderEnum.class.getDeclaredFields()) {
            if (field.getType().equals(GenderEnum.class)) {
                try {
                    GenderEnum instance = (GenderEnum) field.get(null);
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
    public static GenderEnum from(int id) {
        return ALL.get(id);
    }

    @JsonValue
    private final int id;

    GenderEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
