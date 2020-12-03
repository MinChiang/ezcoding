package com.ezcoding.common.foundation.core.lock;

import com.ezcoding.common.foundation.core.lock.impl.DefaultLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.DefaultLockImplement;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:46
 */
public class LockConfig {

    private final Map<Class<? extends LockImplement>, LockImplement> lockImplementMap;
    private final Map<Class<? extends LockIdentification>, LockIdentification> lockIdentificationMap;

    private final LockImplement defaultLockImplement;
    private final LockIdentification defaultLockIdentification;

    LockConfig(Map<Class<? extends LockImplement>, LockImplement> lockImplementMap,
               Map<Class<? extends LockIdentification>, LockIdentification> lockIdentificationMap,
               LockImplement defaultLockImplement,
               LockIdentification defaultLockIdentification) {
        this.lockImplementMap = lockImplementMap;
        this.lockIdentificationMap = lockIdentificationMap;
        this.defaultLockImplement = defaultLockImplement;
        this.defaultLockIdentification = defaultLockIdentification;
    }

    public LockImplement acquireLockImplement(Class<? extends LockImplement> cls) {
        LockImplement lockImplement = lockImplementMap.getOrDefault(cls, defaultLockImplement);
        if (lockImplement instanceof DefaultLockImplement) {
            return ((DefaultLockImplement) lockImplement).getLock();
        }
        return lockImplement;
    }

    public LockIdentification acquireLockIdentification(Class<? extends LockIdentification> cls) {
        LockIdentification lockIdentification = lockIdentificationMap.getOrDefault(cls, defaultLockIdentification);
        if (lockIdentification instanceof DefaultLockIdentification) {
            return ((DefaultLockIdentification) lockIdentification).getDefaultLockIdentification();
        }
        return lockIdentification;
    }

}
