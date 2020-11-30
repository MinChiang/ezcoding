package com.ezcoding.common.foundation.core.lock;

import com.ezcoding.common.foundation.core.lock.impl.DefaultLockProcessor;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:46
 */
public class LockableConfig {

    private final Map<Class<? extends LockProcessor>, LockProcessor> lockableMap;
    private final LockProcessor defaultLockProcessor;

    LockableConfig(Map<Class<? extends LockProcessor>, LockProcessor> lockableMap,
                   LockProcessor defaultLockProcessor) {
        this.lockableMap = lockableMap;
        this.defaultLockProcessor = defaultLockProcessor;
    }

    public LockProcessor acquireLockable(Class<? extends LockProcessor> cls) {
        LockProcessor lockProcessor = lockableMap.getOrDefault(cls, defaultLockProcessor);
        if (lockProcessor instanceof DefaultLockProcessor) {
            return ((DefaultLockProcessor) lockProcessor).getLock();
        }
        return lockProcessor;
    }

}
