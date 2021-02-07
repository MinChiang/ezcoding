package com.ezcoding.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockImplement;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockResult;
import com.ezcoding.common.foundation.core.lock.LockRuntime;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-02-01 17:27
 */
public class RedissonLockImplement implements LockImplement {

    private static final String RLOCK_KEY_NAME = "RedissonLockImplement.RLock";
    private final RedissonClient redissonClient;

    public RedissonLockImplement(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public LockResult lock(String lockKey, LockMetadata lockMetadata, LockRuntime lockRuntime) throws Exception {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.tryLock(lockMetadata.waitTime, lockMetadata.expireTime, lockMetadata.timeUnit)) {
            lockRuntime.lockContext.put(RLOCK_KEY_NAME, lock);
            return LockResult.lockSuccess(lockKey);
        }
        return LockResult.lockFail();
    }

    @Override
    public void unlock(String lockKey, LockMetadata lockMetadata, LockRuntime lockRuntime) {
        RLock lock = (RLock) lockRuntime.lockContext.get(RLOCK_KEY_NAME);
        if (lock != null) {
            lock.unlock();
        }
    }

}
