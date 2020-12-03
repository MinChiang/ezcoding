package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockRuntime;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockImplement;

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

    private final Map<String, Lock> map = new HashMap<>();

    @Override
    public boolean lock(String lockKey, LockRuntime lockRuntime, LockMetadata lockMetadata) throws Exception {
        Lock lock = this.getOrCreate(lockKey, lockRuntime);
        return lock.tryLock(lockMetadata.expireTime, lockMetadata.timeUnit);
    }

    @Override
    public void unlock(String lockKey, LockRuntime lockRuntime, LockMetadata lockMetadata) throws Exception {
        Lock lock = this.getLock(lockKey, lockRuntime);
        lock.unlock();
    }

    /**
     * 获取锁对象
     *
     * @param lockKey  锁对象
     * @param lockRuntime 调用数据
     * @return 锁对象
     */
    private Lock getLock(String lockKey, LockRuntime lockRuntime) {
        return map.get(lockKey);
    }

    /**
     * 获取或构建锁对象
     *
     * @param lockKey  锁对象
     * @param lockRuntime 调用数据
     * @return 锁对象
     */
    private Lock getOrCreate(String lockKey, LockRuntime lockRuntime) {
        Lock lock = getLock(lockKey, lockRuntime);
        if (lock == null) {
            synchronized (this.map) {
                lock = map.computeIfAbsent(lockKey, (key) -> this.createLock(lockKey, lockRuntime));
            }
        }
        return lock;
    }

    /**
     * 创建对象
     *
     * @param lockKey  锁对象
     * @param lockRuntime 调用数据
     * @return 构建的对象
     */
    private Lock createLock(String lockKey, LockRuntime lockRuntime) {
        return new ReentrantLock();
    }

}
