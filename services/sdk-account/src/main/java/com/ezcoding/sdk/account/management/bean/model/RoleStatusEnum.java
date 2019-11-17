package com.ezcoding.sdk.account.management.bean.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 11:49
 */
public enum RoleStatusEnum {

    /**
     * 禁用
     */
    DISABLE(0),

    /**
     * 启用
     */
    ENABLE(1);

    @EnumValue
    @JsonValue
    private final int id;

    RoleStatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @JsonCreator
    public static RoleStatusEnum from(int i) {
        return EnumMappableUtils.map(i, RoleStatusEnum.class);
    }

}
