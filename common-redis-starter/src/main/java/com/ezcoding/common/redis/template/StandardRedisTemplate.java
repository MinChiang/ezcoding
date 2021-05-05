package com.ezcoding.common.redis.template;

import com.ezcoding.common.redis.serializer.StandardRedisKeySerializer;
import com.ezcoding.redis.serializer.StandardRedisKey;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-05-05 22:39
 */
public class StandardRedisTemplate<V> extends RedisTemplate<StandardRedisKey, V> {

    public StandardRedisTemplate() {
        setKeySerializer(new StandardRedisKeySerializer());
    }

}
