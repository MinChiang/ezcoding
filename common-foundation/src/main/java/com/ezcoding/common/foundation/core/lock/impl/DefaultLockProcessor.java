package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockInfo;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockProcessor;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 9:51
 */
public class DefaultLockProcessor implements LockProcessor {

    private LockProcessor lockProcessor;

    @Override
    public boolean lock(LockInfo lockInfo, LockMetadata lockMetadata) throws Exception {
        return this.lockProcessor.lock(lockInfo, lockMetadata);
    }

    @Override
    public void unlock(LockInfo lockInfo, LockMetadata lockMetadata) throws Exception {
        this.lockProcessor.unlock(lockInfo, lockMetadata);
    }

    public LockProcessor getLock() {
        return lockProcessor;
    }

    public void setLock(LockProcessor lockProcessor) {
        this.lockProcessor = lockProcessor;
    }

}
