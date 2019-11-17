package com.ezcoding.gateway.config;

import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import com.ezcoding.common.foundation.core.tools.jwt.TokenTools;
import com.ezcoding.gateway.core.filter.AuthVerifyFilter;
import com.ezcoding.gateway.core.filter.JwtVerifyFilter;
import com.ezcoding.gateway.core.filter.RedisKickOutValidator;
import com.ezcoding.sdk.auth.resource.api.AuthFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-29 9:35
 */
@Configuration
public class FilterConfig {

    @Autowired
    @Qualifier("serializableObjectMapper")
    private ObjectMapper objectMapper;

    @Bean
    public RedisTemplate<String, Date> kickOutRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Date> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<Date> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Date.class);
        objectJackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public JwtVerifyFilter jwtVerifyFilter(AuthSettings authSettings, TokenTools tokenTools, RedisTemplate<String, Date> redisTemplate) {
        RedisKickOutValidator redisKickOutValidator = new RedisKickOutValidator(redisTemplate);
        return new JwtVerifyFilter(authSettings.getHeader(), tokenTools, redisKickOutValidator);
    }

    @Bean
    public AuthVerifyFilter authVerifyFilter(AuthFeignClient authFeignClient) {
        return new AuthVerifyFilter(authFeignClient);
    }

}
