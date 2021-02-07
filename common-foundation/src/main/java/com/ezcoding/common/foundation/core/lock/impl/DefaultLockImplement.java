package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockImplement;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockResult;
import com.ezcoding.common.foundation.core.lock.LockRuntime;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 9:51
 */
public class DefaultLockImplement implements LockImplement {

    private LockImplement lockImplement = new SimpleLockImplement();

    @Override
    public LockResult lock(String lockKey, LockMetadata lockMetadata, LockRuntime lockRuntime) throws Exception {
        return this.lockImplement.lock(lockKey, lockMetadata, lockRuntime);
    }

    @Override
    public void unlock(String lockKey, LockMetadata lockMetadata, LockRuntime lockRuntime) {
        this.lockImplement.unlock(lockKey, lockMetadata, lockRuntime);
    }

    public LockImplement getLockImplement() {
        return lockImplement;
    }

    public void setLockImplement(LockImplement lockImplement) {
        this.lockImplement = lockImplement;
    }

}
