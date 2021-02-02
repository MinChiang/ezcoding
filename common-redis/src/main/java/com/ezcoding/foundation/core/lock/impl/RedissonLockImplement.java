package com.ezcoding.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockContext;
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

    private static final String RLOCK_KEY_NAME = "RedissonLockImplement.RLock";
    private final RedissonClient redissonClient;

    public RedissonLockImplement(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public LockResult lock(String lockKey, LockMetadata lockMetadata, Object target, Object[] args, LockContext lockContext) throws Exception {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.tryLock(lockMetadata.waitTime, lockMetadata.expireTime, lockMetadata.timeUnit)) {
            lockContext.put(RLOCK_KEY_NAME, lock);
            return LockResult.lockSuccess(lockKey);
        }
        return LockResult.lockFail();
    }

    @Override
    public void unlock(String lockKey, LockMetadata lockMetadata, Object target, Object[] args, LockContext lockContext) {
        RLock lock = (RLock) lockContext.get(RLOCK_KEY_NAME);
        if (lock != null) {
            lock.unlock();
        }
    }

}
