package com.ezcoding.common.foundation.core.metadata;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 16:21
 */
public enum SerializationTypeEnum {

    /**
     * 字符串
     */
    STRING("string"),

    /**
     * JSON格式
     */
    JSON("json");

    private static final Map<String, SerializationTypeEnum> ALL = Collections.unmodifiableMap(Arrays.stream(SerializationTypeEnum.class.getEnumConstants()).collect(Collectors.toMap(value -> value.id, Function.identity())));

    /**
     * 转换
     *
     * @param id id
     * @return 对应类别
     */
    public static SerializationTypeEnum from(String id) {
        return ALL.get(id);
    }

    public final String id;

    SerializationTypeEnum(String id) {
        this.id = id;
    }

}
