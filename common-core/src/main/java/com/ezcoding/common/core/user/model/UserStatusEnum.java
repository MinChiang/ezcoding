package com.ezcoding.common.core.user.model;

import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
    private final int id;

    UserStatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @JsonCreator
    public static UserStatusEnum from(int id) {
        return EnumMappableUtils.map(id, UserStatusEnum.class);
    }

}
