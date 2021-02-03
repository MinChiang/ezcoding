package com.ezcoding.common.redis.starter;

import com.ezcoding.foundation.core.lock.impl.RedissonLockImplement;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
@Configuration
public class RedisAutoConfiguration {

    @Bean("defaultLockImplement")
    @ConditionalOnMissingBean
    public RedissonLockImplement defaultLockImplement(RedissonClient redissonClient) {
        return new RedissonLockImplement(redissonClient);
    }

}
