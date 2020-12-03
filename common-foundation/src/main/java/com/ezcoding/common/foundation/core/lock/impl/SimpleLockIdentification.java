package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockIdentification;
import com.ezcoding.common.foundation.core.lock.LockRuntime;
import com.ezcoding.common.foundation.core.lock.LockMetadata;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 22:28
 */
public class SimpleLockIdentification implements LockIdentification {

    @Override
    public String identify(LockRuntime lockRuntime, LockMetadata lockMetadata) {
        String key = lockMetadata.key.isEmpty() ? lockRuntime.getMethod().getName() : lockMetadata.key;
        String prefix = lockMetadata.prefix.isEmpty() ? lockRuntime.getTarget().getClass().getName() : lockMetadata.prefix;
        return key + "#" + prefix;
    }

}
