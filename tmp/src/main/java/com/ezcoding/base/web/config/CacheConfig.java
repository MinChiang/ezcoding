package com.ezcoding.base.web.config;

import com.ezcoding.common.foundation.core.application.ApplicationMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-23 22:33
 */
@Configuration
@EnableCaching
public class CacheConfig {

    private static final String CACHE_NAME = ":cache:";
    private static final long TTL_SECONDS = 600;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private ApplicationMetadata applicationMetadata;
    @Autowired
    @Qualifier("serializableObjectMapper")
    private ObjectMapper objectMapper;

    @Bean
    public CacheManager redisCacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(TTL_SECONDS))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)))
                .prefixKeysWith(applicationMetadata.getCategory() + CACHE_NAME);

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .initialCacheNames(ImmutableSet.of("default"))
                .build();
    }

}
