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
    public boolean lock(String lockKey, LockInfo lockInfo, LockMetadata lockMetadata) throws Exception {
        return this.lockProcessor.lock(lockKey, lockInfo, lockMetadata);
    }

    @Override
    public void unlock(String lockKey, LockInfo lockInfo, LockMetadata lockMetadata) throws Exception {
        this.lockProcessor.unlock(lockKey, lockInfo, lockMetadata);
    }

    public LockProcessor getLock() {
        return lockProcessor;
    }

    public void setLock(LockProcessor lockProcessor) {
        this.lockProcessor = lockProcessor;
    }

}
