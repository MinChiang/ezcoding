package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockIdentification;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockProcessor;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 22:28
 */
public class SimpleLockIdentification implements LockIdentification {

    @Override
    public String identify(LockProcessor lockProcessor) {
        LockMetadata lockMetadata = lockProcessor.getLockMetadata();
        String key = lockMetadata.key.isEmpty() ? lockProcessor.getMethod().getName() : lockMetadata.key;
        String prefix = lockMetadata.prefix.isEmpty() ? lockProcessor.getMethod().getName() : lockMetadata.prefix;
        return key + "#" + prefix;
    }

}
