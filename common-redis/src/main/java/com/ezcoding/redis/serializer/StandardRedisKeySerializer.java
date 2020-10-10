package com.ezcoding.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-22 16:00
 */
public class StandardRedisKeySerializer implements RedisSerializer<StandardRedisKey> {

    protected char nameableModuleSplitterChar = ':';
    protected String nameableModuleSplitterString = ":";

    @Override
    public byte[] serialize(StandardRedisKey standardRedisKey) throws SerializationException {
        if (standardRedisKey == null) {
            return null;
        }
        return StringRedisSerializer.UTF_8.serialize(standardRedisKey.getFunctionLayerModule().getPath(nameableModuleSplitterString) + nameableModuleSplitterString + standardRedisKey.getKey());
    }

    @Override
    public StandardRedisKey deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        String deserialize = StringRedisSerializer.UTF_8.deserialize(bytes);
        if (deserialize == null) {
            return null;
        }

        int count = 3;
        int begin = 0;
        for (; begin < deserialize.length() && count != 0; begin++) {
            if (deserialize.charAt(begin) == nameableModuleSplitterChar) {
                count--;
            }
        }

        return StandardRedisKey.createWithoutCheck(count == 0 ? deserialize.substring(begin) : deserialize);
    }

}
