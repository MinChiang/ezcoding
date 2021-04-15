package com.ezcoding.common.foundation.core.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-03 15:04
 */
public enum BooleanTypeEnum implements EnumMappable<Integer> {

    /**
     * 真
     */
    TRUE(1),

    /**
     * 假
     */
    FALSE(0);

    private static final Map<Integer, BooleanTypeEnum> ALL = Collections.unmodifiableMap(Arrays.stream(BooleanTypeEnum.class.getEnumConstants()).collect(Collectors.toMap(value -> value.id, value -> value)));

    /**
     * 转换
     *
     * @param id id
     * @return 对应类别
     */
    public static BooleanTypeEnum from(int id) {
        return ALL.get(id);
    }

    public final int id;

    BooleanTypeEnum(int id) {
        this.id = id;
    }

    @Override
    public Integer map() {
        return this.id;
    }
}
