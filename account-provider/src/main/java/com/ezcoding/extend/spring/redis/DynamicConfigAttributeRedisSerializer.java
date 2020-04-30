package com.ezcoding.extend.spring.redis;

import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-22 14:27
 */
public class DynamicConfigAttributeRedisSerializer implements RedisSerializer<DynamicConfigAttribute> {

    @Override
    public byte[] serialize(DynamicConfigAttribute dynamicConfigAttribute) throws SerializationException {
        return (dynamicConfigAttribute == null ? null : dynamicConfigAttribute.getAttribute().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public DynamicConfigAttribute deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }

        try {
            return DynamicConfigAttribute.create(new String(bytes, StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
