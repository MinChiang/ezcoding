package com.ezcoding.config;

import com.ezcoding.common.foundation.core.tools.uuid.IUUIDProducer;
import com.ezcoding.common.foundation.core.tools.verification.NumberVerificationCodeGenerator;
import com.ezcoding.common.foundation.core.tools.verification.OriginalVerificationCodeGenerator;
import com.ezcoding.extend.user.LocalUserProxy;
import com.ezcoding.module.user.bean.model.VerificationInfo;
import com.ezcoding.module.user.core.verification.RedisVerificationServiceImpl;
import com.ezcoding.module.user.dao.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-15 8:58
 */
@Configuration
public class UserConfig {

    /**
     * 覆盖对应获取用户信息的远程调用
     *
     * @return 用户代理
     */
    @Bean
    public LocalUserProxy localUserProxy(UserMapper userMapper) {
        return new LocalUserProxy(userMapper);
    }

    @Bean(name = "imageVerificationService")
    public RedisVerificationServiceImpl imageVerificationService(RedisTemplate<String, VerificationInfo> verificationInfoRedisTemplate, OriginalVerificationCodeGenerator originalVerificationCodeGenerator) {
        RedisVerificationServiceImpl redisVerificationServiceImpl = new RedisVerificationServiceImpl();
        redisVerificationServiceImpl.setRedisTemplate(verificationInfoRedisTemplate);
        redisVerificationServiceImpl.setVerificationCodeGenerator(originalVerificationCodeGenerator);
        return redisVerificationServiceImpl;
    }

    @Bean(name = "numberVerificationService")
    public RedisVerificationServiceImpl numberVerificationService(RedisTemplate<String, VerificationInfo> verificationInfoRedisTemplate, NumberVerificationCodeGenerator numberVerificationCodeGenerator, IUUIDProducer producer) {
        RedisVerificationServiceImpl redisVerificationServiceImpl = new RedisVerificationServiceImpl();
        redisVerificationServiceImpl.setRedisTemplate(verificationInfoRedisTemplate);
        redisVerificationServiceImpl.setVerificationCodeGenerator(numberVerificationCodeGenerator);
        redisVerificationServiceImpl.setReceiptProducer(producer);
        return redisVerificationServiceImpl;
    }

    @Bean
    public RedisTemplate<String, VerificationInfo> verificationInfoRedisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
        RedisTemplate<String, VerificationInfo> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<VerificationInfo> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<VerificationInfo>(VerificationInfo.class);
        objectJackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        return template;
    }

}