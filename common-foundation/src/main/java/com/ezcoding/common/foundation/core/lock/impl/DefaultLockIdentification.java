package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockConfig;
import com.ezcoding.common.foundation.core.lock.LockIdentification;
import com.ezcoding.common.foundation.core.lock.LockMetadata;

import java.lang.reflect.Method;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 22:28
 */
public class DefaultLockIdentification implements LockIdentification {

    private LockIdentification defaultLockIdentification = new SimpleLockIdentification();

    @Override
    public String identify(LockMetadata lockMetadata, LockConfig lockConfig, Method method) {
        return defaultLockIdentification.identify(lockMetadata, lockConfig, method);
    }

    public LockIdentification getDefaultLockIdentification() {
        return defaultLockIdentification;
    }

    public void setDefaultLockIdentification(LockIdentification defaultLockIdentification) {
        this.defaultLockIdentification = defaultLockIdentification;
    }

}
