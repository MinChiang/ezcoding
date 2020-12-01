package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockInfo;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 11:40
 */
public class SimpleLockProcessor implements LockProcessor {

    private final Map<String, Lock> map = new HashMap<>();

    @Override
    public boolean lock(String lockKey, LockInfo lockInfo, LockMetadata lockMetadata) throws Exception {
        Lock lock = this.getOrCreate(lockKey, lockInfo);
        return lock.tryLock(lockMetadata.expireTime, lockMetadata.timeUnit);
    }

    @Override
    public void unlock(String lockKey, LockInfo lockInfo, LockMetadata lockMetadata) throws Exception {
        Lock lock = this.getLock(lockKey, lockInfo);
        lock.unlock();
    }

    /**
     * 获取锁对象
     *
     * @param lockKey  锁对象
     * @param lockInfo 调用数据
     * @return 锁对象
     */
    private Lock getLock(String lockKey, LockInfo lockInfo) {
        return map.get(lockKey);
    }

    /**
     * 获取或构建锁对象
     *
     * @param lockKey  锁对象
     * @param lockInfo 调用数据
     * @return 锁对象
     */
    private Lock getOrCreate(String lockKey, LockInfo lockInfo) {
        Lock lock = getLock(lockKey, lockInfo);
        if (lock == null) {
            synchronized (this.map) {
                lock = map.computeIfAbsent(lockKey, (key) -> this.createLock(lockKey, lockInfo));
            }
        }
        return lock;
    }

    /**
     * 创建对象
     *
     * @param lockKey  锁对象
     * @param lockInfo 调用数据
     * @return 构建的对象
     */
    private Lock createLock(String lockKey, LockInfo lockInfo) {
        return new ReentrantLock();
    }

}
