package com.ezcoding.common.redis.starter;

import com.ezcoding.common.foundation.core.lock.LockImplement;
import com.ezcoding.common.foundation.starter.FoundationConfigurer;
import com.ezcoding.foundation.core.lock.impl.RedissonLockImplement;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
@Configuration
public class RedisAutoConfiguration implements FoundationConfigurer {

    @Autowired(required = false)
    private RedissonLockImplement redissonLockImplement;

    @Bean("defaultLockImplement")
    @ConditionalOnMissingBean
    public RedissonLockImplement defaultLockImplement(RedissonClient redissonClient) {
        return new RedissonLockImplement(redissonClient);
    }

    @Override
    public void registerLockImplement(List<LockImplement> lockImplements) {
        if (redissonLockImplement != null) {
            lockImplements.add(redissonLockImplement);
        }
    }

}
