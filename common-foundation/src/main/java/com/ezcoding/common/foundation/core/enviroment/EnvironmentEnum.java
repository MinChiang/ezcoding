package com.ezcoding.common.foundation.core.enviroment;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-29 17:56
 */
public enum EnvironmentEnum {

    /**
     * 本地
     */
    LOCAL("local", "本地"),

    /**
     * 开发
     */
    DEV("dev", "开发"),

    /**
     * 测试
     */
    TEST("test", "测试"),

    /**
     * 生产
     */
    PROD("prod", "生产");

    private static final Map<String, EnvironmentEnum> ALL = Collections.unmodifiableMap(Arrays.stream(EnvironmentEnum.class.getEnumConstants()).collect(Collectors.toMap(value -> value.id, Function.identity())));

    /**
     * 转换
     *
     * @param id id
     * @return 对应类别
     */
    public static EnvironmentEnum from(String id) {
        return ALL.get(id);
    }

    public final String id;

    public final String description;

    EnvironmentEnum(String id, String description) {
        this.id = id;
        this.description = description;
    }

}
