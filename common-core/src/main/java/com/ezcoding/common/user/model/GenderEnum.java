package com.ezcoding.common.user.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    @EnumValue
    @JsonValue
    private final int id;

    GenderEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @JsonCreator
    public static GenderEnum from(int i) {
        return EnumMappableUtils.map(i, GenderEnum.class);
    }
}
