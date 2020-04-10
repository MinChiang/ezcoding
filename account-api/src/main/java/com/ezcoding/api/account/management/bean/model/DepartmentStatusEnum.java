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
public enum DepartmentStatusEnum {

    /**
     * 运营中
     */
    RUNNING(0),

    /**
     * 已解散
     */
    DISMISSED(1),

    /**
     * 已删除
     */
    DELETED(2);

    private static final Map<Integer, DepartmentStatusEnum> ALL = new HashMap<Integer, DepartmentStatusEnum>();

    static {
        for (Field field : DepartmentStatusEnum.class.getDeclaredFields()) {
            if (field.getType().equals(DepartmentStatusEnum.class)) {
                try {
                    DepartmentStatusEnum instance = (DepartmentStatusEnum) field.get(null);
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
    public static DepartmentStatusEnum from(int id) {
        return ALL.get(id);
    }

    @EnumValue
    @JsonValue
    private final int id;

    DepartmentStatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

}
