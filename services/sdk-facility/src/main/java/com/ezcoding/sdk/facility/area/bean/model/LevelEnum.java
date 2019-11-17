package com.ezcoding.sdk.facility.area.bean.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 5级行政区域常量
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-19 14:52
 */
public enum LevelEnum {

    /**
     * 省级
     */
    PROVINCE(0),

    /**
     * 市级
     */
    CITY(1),

    /**
     * 区级
     */
    DISTRICT(2),

    /**
     * 办事处
     */
    OFFICE(3),

    /**
     * 居委会
     */
    COMMITTEE(4);

    @EnumValue
    @JsonValue
    private final int id;

    LevelEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
