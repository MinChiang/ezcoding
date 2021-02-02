package com.ezcoding.common.core.user.model;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static final Map<Integer, DeviceTypeEnum> ALL = Arrays.stream(DeviceTypeEnum.class.getEnumConstants()).collect(Collectors.toMap(value -> value.id, value -> value));

    public static DeviceTypeEnum from(int id) {
        return ALL.get(id);
    }

    public final int id;

    DeviceTypeEnum(int id) {
        this.id = id;
    }

}
