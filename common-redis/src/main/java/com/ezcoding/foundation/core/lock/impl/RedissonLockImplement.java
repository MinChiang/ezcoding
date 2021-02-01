package com.ezcoding.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockImplement;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockResult;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-02-01 17:27
 */
public class RedissonLockImplement implements LockImplement {

    private RedissonClient redissonClient;

    public RedissonLockImplement(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public LockResult lock(String lockKey, LockMetadata lockMetadata, Object target, Object[] args) throws Exception {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.tryLock(lockMetadata.waitTime, lockMetadata.expireTime, lockMetadata.timeUnit)) {
            return LockResult.lockSuccess(lockKey);
        }
        return LockResult.lockFail();
    }

    @Override
    public void unlock(String lockKey, LockMetadata lockMetadata, Object target, Object[] args) {

    }

}
