package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockIdentification;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockRuntime;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 22:28
 */
public class DefaultLockIdentification implements LockIdentification {

    private LockIdentification defaultLockIdentification = new SimpleLockIdentification();

    @Override
    public String identify(LockMetadata lockMetadata, LockRuntime lockRuntime) {
        return defaultLockIdentification.identify(lockMetadata, lockRuntime);
    }

    public LockIdentification getDefaultLockIdentification() {
        return defaultLockIdentification;
    }

    public void setDefaultLockIdentification(LockIdentification defaultLockIdentification) {
        this.defaultLockIdentification = defaultLockIdentification;
    }

}
