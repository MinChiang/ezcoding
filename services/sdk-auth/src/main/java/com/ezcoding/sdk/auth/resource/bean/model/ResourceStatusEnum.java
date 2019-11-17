package com.ezcoding.sdk.auth.resource.bean.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 11:30
 */
public enum ResourceStatusEnum {

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

    ResourceStatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @JsonCreator
    public static ResourceStatusEnum from(int i) {
        return EnumMappableUtils.map(i, ResourceStatusEnum.class);
    }

}
