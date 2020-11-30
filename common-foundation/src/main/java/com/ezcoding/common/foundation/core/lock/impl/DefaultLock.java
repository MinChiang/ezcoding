package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockInfo;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.Lockable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 9:51
 */
public class DefaultLock implements Lockable {

    private Lockable lockable;

    @Override
    public boolean lock(LockInfo lockInfo, LockMetadata lockMetadata) throws Exception {
        return this.lockable.lock(lockInfo, lockMetadata);
    }

    @Override
    public void unlock(LockInfo lockInfo, LockMetadata lockMetadata) throws Exception {
        this.lockable.unlock(lockInfo, lockMetadata);
    }

    public Lockable getLock() {
        return lockable;
    }

    public void setLock(Lockable lockable) {
        this.lockable = lockable;
    }

}
