package com.ezcoding.sdk.auth.resource.bean.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 10:28
 */
public enum ResourceTypeEnum {

    /**
     * 权限
     */
    PERMISSION(0),

    /**
     * 访问路径
     */
    ACCESS_PATH(1);

    @EnumValue
    @JsonValue
    private final int id;

    ResourceTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @JsonCreator
    public static ResourceTypeEnum from(int i) {
        return EnumMappableUtils.map(i, ResourceTypeEnum.class);
    }

}
