package com.ezcoding.common.core.user.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Integer, GenderEnum> ALL = new HashMap<>();

    static {
        Arrays.stream(GenderEnum.class.getEnumConstants()).forEach(value -> ALL.put(value.getId(), value));
    }

    /**
     * 转换
     *
     * @param id id
     * @return 对应类别
     */
    public static GenderEnum from(int id) {
        return ALL.get(id);
    }

    private final int id;

    GenderEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
