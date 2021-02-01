package com.ezcoding.common.foundation.core.lock;

import com.ezcoding.common.foundation.core.lock.impl.DefaultLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.DefaultLockImplement;
import com.ezcoding.common.foundation.core.lock.impl.SimpleLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.SimpleLockImplement;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:46
 */
public class LockConfig {

    private final Map<Class<? extends LockImplement>, LockImplement> lockImplementMap;
    private final Map<Class<? extends LockIdentification>, LockIdentification> lockIdentificationMap;

    private LockImplement defaultLockImplement = new SimpleLockImplement();
    private LockIdentification defaultLockIdentification = new SimpleLockIdentification();

    public LockConfig(Map<Class<? extends LockImplement>, LockImplement> lockImplementMap, Map<Class<? extends LockIdentification>, LockIdentification> lockIdentificationMap, LockImplement defaultLockImplement, LockIdentification defaultLockIdentification) {
        this.lockImplementMap = lockImplementMap;
        this.lockIdentificationMap = lockIdentificationMap;
        this.defaultLockImplement = defaultLockImplement;
        this.defaultLockIdentification = defaultLockIdentification;
    }

    /**
     * 获取锁实现类
     *
     * @param cls 锁实现类
     * @return 锁实现实例
     */
    public LockImplement acquireLockImplement(Class<? extends LockImplement> cls) {
        LockImplement lockImplement = lockImplementMap.getOrDefault(cls, defaultLockImplement);
        if (lockImplement instanceof DefaultLockImplement) {
            return ((DefaultLockImplement) lockImplement).getLock();
        }
        return lockImplement;
    }

    /**
     * 获取锁id
     *
     * @param cls 锁id实现类
     * @return 锁id实例
     */
    public LockIdentification acquireLockIdentification(Class<? extends LockIdentification> cls) {
        LockIdentification lockIdentification = lockIdentificationMap.getOrDefault(cls, defaultLockIdentification);
        if (lockIdentification instanceof DefaultLockIdentification) {
            return ((DefaultLockIdentification) lockIdentification).getDefaultLockIdentification();
        }
        return lockIdentification;
    }

}
