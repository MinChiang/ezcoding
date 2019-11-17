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
public enum PositionStatusEnum {

    /**
     * 正常运营中
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

    @EnumValue
    @JsonValue
    private final int id;

    PositionStatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @JsonCreator
    public static PositionStatusEnum from(int i) {
        return EnumMappableUtils.map(i, PositionStatusEnum.class);
    }

}
