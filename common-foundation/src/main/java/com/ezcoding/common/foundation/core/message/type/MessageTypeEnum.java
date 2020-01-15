package com.ezcoding.common.foundation.core.message.type;

import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
    private final String type;

    MessageTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @JsonCreator
    public static MessageTypeEnum from(String id) {
        return EnumMappableUtils.map(id, MessageTypeEnum.class);
    }

}
