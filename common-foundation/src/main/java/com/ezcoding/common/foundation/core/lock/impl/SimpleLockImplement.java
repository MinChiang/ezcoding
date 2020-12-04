package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockImplement;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockProcessor;
import com.ezcoding.common.foundation.core.lock.LockResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 11:40
 */
public class SimpleLockImplement implements LockImplement {

    private final Map<String, Lock> map = Collections.synchronizedMap(new HashMap<>());

    @Override
    public LockResult lock(String lockKey, LockProcessor lockProcessor, Object target, Object[] args) throws Exception {
        Lock lock = this.getOrCreate(lockKey);
        LockMetadata lockMetadata = lockProcessor.getLockMetadata();
        if (lock.tryLock(lockMetadata.expireTime, lockMetadata.timeUnit)) {
            return new LockResult(lockKey);
        }
        return new LockResult();
    }

    @Override
    public void unlock(String lockKey, LockProcessor lockProcessor, Object target, Object[] args) {
        Lock lock = this.getLock(lockKey);
        if (lock != null) {
            lock.unlock();
        }
    }

    /**
     * 获取锁对象
     *
     * @param lockKey 锁对象
     * @return 锁对象
     */
    private Lock getLock(String lockKey) {
        return map.get(lockKey);
    }

    /**
     * 获取或构建锁对象
     *
     * @param lockKey 锁对象
     * @return 锁对象
     */
    private Lock getOrCreate(String lockKey) {
        return map.computeIfAbsent(lockKey, (key) -> this.createLock(lockKey));
    }

    /**
     * 创建对象
     *
     * @param lockKey 锁对象
     * @return 构建的对象
     */
    private Lock createLock(String lockKey) {
        return new ReentrantLock();
    }

}
