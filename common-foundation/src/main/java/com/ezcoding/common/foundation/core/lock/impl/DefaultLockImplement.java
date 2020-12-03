package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockRuntime;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockImplement;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 9:51
 */
public class DefaultLockImplement implements LockImplement {

    private LockImplement lockImplement;

    @Override
    public boolean lock(String lockKey, LockRuntime lockRuntime, LockMetadata lockMetadata) throws Exception {
        return this.lockImplement.lock(lockKey, lockRuntime, lockMetadata);
    }

    @Override
    public void unlock(String lockKey, LockRuntime lockRuntime, LockMetadata lockMetadata) throws Exception {
        this.lockImplement.unlock(lockKey, lockRuntime, lockMetadata);
    }

    public LockImplement getLock() {
        return lockImplement;
    }

    public void setLock(LockImplement lockImplement) {
        this.lockImplement = lockImplement;
    }

}
