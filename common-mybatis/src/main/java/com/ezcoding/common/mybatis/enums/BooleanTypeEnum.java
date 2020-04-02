package com.ezcoding.common.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-03 15:04
 */
public enum BooleanTypeEnum {

    /**
     * 真
     */
    TRUE(1),

    /**
     * 假
     */
    FALSE(0);

    private static final Map<Integer, BooleanTypeEnum> ALL = new HashMap<>();

    static {
        for (Field field : BooleanTypeEnum.class.getDeclaredFields()) {
            if (field.getType().equals(BooleanTypeEnum.class)) {
                try {
                    BooleanTypeEnum instance = (BooleanTypeEnum) field.get(null);
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
    public static BooleanTypeEnum from(int id) {
        return ALL.get(id);
    }

    @EnumValue
    @JsonValue
    private final int id;

    BooleanTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
