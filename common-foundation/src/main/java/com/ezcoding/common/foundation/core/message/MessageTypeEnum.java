package com.ezcoding.common.foundation.core.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public enum MessageTypeEnum {

    /**
     * JSON格式
     */
    JSON("application/json"),

    /**
     * XML格式
     */
    XML("text/xml");

    private static final Map<String, MessageTypeEnum> ALL = new HashMap<String, MessageTypeEnum>();

    static {
        for (Field field : MessageTypeEnum.class.getDeclaredFields()) {
            if (field.getType().equals(MessageTypeEnum.class)) {
                try {
                    MessageTypeEnum instance = (MessageTypeEnum) field.get(null);
                    ALL.put(instance.type, instance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 转换
     *
     * @param id id
     * @return 对应类别
     */
    @JsonCreator
    public static MessageTypeEnum from(String id) {
        return ALL.get(id);
    }

    @JsonValue
    private final String type;

    MessageTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
