package com.ezcoding.common.core.user.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Integer, DeviceTypeEnum> ALL = new HashMap<>();

    static {
        Arrays.stream(DeviceTypeEnum.class.getEnumConstants()).forEach(value -> ALL.put(value.getId(), value));
    }

    public static DeviceTypeEnum from(int id) {
        return ALL.get(id);
    }

    private final int id;

    DeviceTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
