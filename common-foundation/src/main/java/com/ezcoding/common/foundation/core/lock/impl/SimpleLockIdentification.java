package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockIdentification;
import com.ezcoding.common.foundation.core.lock.LockInfo;
import com.ezcoding.common.foundation.core.lock.LockMetadata;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 22:28
 */
public class SimpleLockIdentification implements LockIdentification {

    @Override
    public String identify(LockInfo lockInfo, LockMetadata lockMetadata) {
        String key = lockMetadata.key.isEmpty() ? lockInfo.getMethod().getName() : lockMetadata.key;
        String prefix = lockMetadata.prefix.isEmpty() ? lockInfo.getTarget().getClass().getName() : lockMetadata.prefix;
        return key + "#" + prefix;
    }

}
