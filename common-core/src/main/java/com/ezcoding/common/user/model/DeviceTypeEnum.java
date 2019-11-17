package com.ezcoding.common.user.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-16 21:48
 */
public enum DeviceTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(0),

    /**
     * 电脑
     */
    COMPUTER(1),

    /**
     * 手机
     */
    MOBILE(2),

    /**
     * 平板
     */
    TABLET(3),

    /**
     * 游戏平台
     */
    GAME_CONSOLE(4),

    /**
     * 数字设备
     */
    DMR(5),

    /**
     * 可穿戴设备
     */
    WEARABLE(6);

    @EnumValue
    @JsonValue
    private final int id;

    DeviceTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @JsonCreator
    public static DeviceTypeEnum from(int i) {
        return EnumMappableUtils.map(i, DeviceTypeEnum.class);
    }

}
