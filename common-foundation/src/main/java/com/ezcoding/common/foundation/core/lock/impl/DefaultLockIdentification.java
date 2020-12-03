package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockIdentification;
import com.ezcoding.common.foundation.core.lock.LockProcessor;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 22:28
 */
public class DefaultLockIdentification implements LockIdentification {

    private LockIdentification defaultLockIdentification = new SimpleLockIdentification();

    @Override
    public String identify(LockProcessor lockProcessor) {
        return defaultLockIdentification.identify(lockProcessor);
    }

    public LockIdentification getDefaultLockIdentification() {
        return defaultLockIdentification;
    }

    public void setDefaultLockIdentification(LockIdentification defaultLockIdentification) {
        this.defaultLockIdentification = defaultLockIdentification;
    }

}
