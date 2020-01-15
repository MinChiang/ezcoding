package com.ezcoding.common.core.user.model;

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

    @JsonValue
    private final int id;

    GenderEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @JsonCreator
    public static GenderEnum from(int id) {
        return EnumMappableUtils.map(id, GenderEnum.class);
    }
}
