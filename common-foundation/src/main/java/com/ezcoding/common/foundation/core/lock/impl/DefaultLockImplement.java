package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockContext;
import com.ezcoding.common.foundation.core.lock.LockImplement;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockResult;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 9:51
 */
public class DefaultLockImplement implements LockImplement {

    private LockImplement lockImplement = new SimpleLockImplement();

    @Override
    public LockResult lock(String lockKey, LockMetadata lockMetadata, Object target, Object[] args, LockContext lockContext) throws Exception {
        return this.lockImplement.lock(lockKey, lockMetadata, target, args, lockContext);
    }

    @Override
    public void unlock(String lockKey, LockMetadata lockMetadata, Object target, Object[] args, LockContext lockContext) {
        this.lockImplement.unlock(lockKey, lockMetadata, target, args, lockContext);
    }

    public LockImplement getLock() {
        return lockImplement;
    }

    public void setLock(LockImplement lockImplement) {
        this.lockImplement = lockImplement;
    }

}
