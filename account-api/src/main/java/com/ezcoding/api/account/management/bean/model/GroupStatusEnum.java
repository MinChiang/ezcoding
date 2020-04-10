package com.ezcoding.api.account.management.bean.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 11:49
 */
public enum GroupStatusEnum {

    /**
     * 禁用
     */
    DISABLE(0),

    /**
     * 启用
     */
    ENABLE(1);

    private static final Map<Integer, GroupStatusEnum> ALL = new HashMap<Integer, GroupStatusEnum>();

    static {
        for (Field field : GroupStatusEnum.class.getDeclaredFields()) {
            if (field.getType().equals(GroupStatusEnum.class)) {
                try {
                    GroupStatusEnum instance = (GroupStatusEnum) field.get(null);
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
    public static GroupStatusEnum from(int id) {
        return ALL.get(id);
    }

    @EnumValue
    @JsonValue
    private final int id;

    GroupStatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

}
