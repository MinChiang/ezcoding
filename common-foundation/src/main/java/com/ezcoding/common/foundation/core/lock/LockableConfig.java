package com.ezcoding.common.foundation.core.lock;

import com.ezcoding.common.foundation.core.lock.impl.DefaultLock;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:46
 */
public class LockableConfig {

    private final Map<Class<? extends Lockable>, Lockable> lockableMap;
    private final Lockable defaultLockable;

    LockableConfig(Map<Class<? extends Lockable>, Lockable> lockableMap,
                   Lockable defaultLockable) {
        this.lockableMap = lockableMap;
        this.defaultLockable = defaultLockable;
    }

    public Lockable acquireLockable(Class<? extends Lockable> cls) {
        Lockable lockable = lockableMap.getOrDefault(cls, defaultLockable);
        if (lockable instanceof DefaultLock) {
            return ((DefaultLock) lockable).getLock();
        }
        return lockable;
    }

}
