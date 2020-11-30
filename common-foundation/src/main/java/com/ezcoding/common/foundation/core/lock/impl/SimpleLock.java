package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockInfo;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockProcessor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 11:40
 */
public class SimpleLock implements LockProcessor {

    private final Map<Method, Lock> map = new WeakHashMap<>();

    @Override
    public boolean lock(LockInfo lockInfo, LockMetadata lockMetadata) throws Exception {
        Lock lock = this.getOrCreate(lockInfo);
        return lock.tryLock(lockMetadata.expireTime, lockMetadata.timeUnit);
    }

    @Override
    public void unlock(LockInfo lockInfo, LockMetadata lockMetadata) throws Exception {
        Lock lock = this.getLock(lockInfo);
        lock.unlock();
    }

    /**
     * 获取锁对象
     *
     * @param lockInfo 调用数据
     * @return 锁对象
     */
    private Lock getLock(LockInfo lockInfo) {
        return map.get(lockInfo.getMethod());
    }

    /**
     * 获取或构建锁对象
     *
     * @param lockInfo 调用数据
     * @return 锁对象
     */
    private Lock getOrCreate(LockInfo lockInfo) {
        Lock lock = getLock(lockInfo);
        if (lock == null) {
            synchronized (this.map) {
                lock = map.computeIfAbsent(lockInfo.getMethod(), (key) -> this.createLock(lockInfo));
            }
        }
        return lock;
    }

    /**
     * 创建对象
     *
     * @param lockInfo 调用数据
     * @return 构建的对象
     */
    private Lock createLock(LockInfo lockInfo) {
        return new ReentrantLock();
    }

}
