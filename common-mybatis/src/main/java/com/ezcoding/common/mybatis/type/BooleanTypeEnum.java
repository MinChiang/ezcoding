package com.ezcoding.common.mybatis.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-03 15:04
 */
public enum BooleanTypeEnum {

    /**
     * 真
     */
    TRUE(1),

    /**
     * 假
     */
    FALSE(0);

    @EnumValue
    @JsonValue
    private final int id;

    BooleanTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @JsonCreator
    public static BooleanTypeEnum from(int i) {
        return EnumMappableUtils.map(i, BooleanTypeEnum.class);
    }

}
