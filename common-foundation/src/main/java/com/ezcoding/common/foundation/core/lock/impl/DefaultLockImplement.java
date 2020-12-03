package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockImplement;
import com.ezcoding.common.foundation.core.lock.LockProcessor;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 9:51
 */
public class DefaultLockImplement implements LockImplement {

    private LockImplement lockImplement = new SimpleLockImplement();

    @Override
    public boolean lock(String lockKey, LockProcessor lockProcessor, Object target, Object[] args) throws Exception {
        return this.lockImplement.lock(lockKey, lockProcessor, target, args);
    }

    @Override
    public void unlock(String lockKey, LockProcessor lockProcessor, Object target, Object[] args) {
        this.lockImplement.unlock(lockKey, lockProcessor, target, args);
    }

    public LockImplement getLock() {
        return lockImplement;
    }

    public void setLock(LockImplement lockImplement) {
        this.lockImplement = lockImplement;
    }

}
