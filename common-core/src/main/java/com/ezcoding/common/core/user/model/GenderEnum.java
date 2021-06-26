package com.ezcoding.common.core.user.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-04-18 16:15
 */
public enum GenderEnum {

    /**
     * 未知
     */
    UNKNOWN(0),

    /**
     * 男
     */
    MALE(1),

    /**
     * 女
     */
    FEMALE(2);

    private static final Map<Integer, GenderEnum> ALL = Collections.unmodifiableMap(Arrays.stream(GenderEnum.class.getEnumConstants()).collect(Collectors.toMap(value -> value.id, Function.identity())));

    /**
     * 转换
     *
     * @param id id
     * @return 对应类别
     */
    public static GenderEnum from(int id) {
        return ALL.get(id);
    }

    public final int id;

    GenderEnum(int id) {
        this.id = id;
    }

}
