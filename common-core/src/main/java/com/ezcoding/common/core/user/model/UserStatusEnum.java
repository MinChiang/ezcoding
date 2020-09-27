package com.ezcoding.common.core.user.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-16 21:48
 */
public enum UserStatusEnum {

    /**
     * 正常
     */
    NORMAL(0),

    /**
     * 已锁定
     */
    LOCKED(1),

    /**
     * 已注销
     */
    CANCELED(2);

    private static final Map<Integer, UserStatusEnum> ALL = new HashMap<>();

    static {
        Arrays.stream(UserStatusEnum.class.getEnumConstants()).forEach(value -> ALL.put(value.getId(), value));
    }

    /**
     * 转换
     *
     * @param id id
     * @return 对应类别
     */
    public static UserStatusEnum from(int id) {
        return ALL.get(id);
    }

    private final int id;

    UserStatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
