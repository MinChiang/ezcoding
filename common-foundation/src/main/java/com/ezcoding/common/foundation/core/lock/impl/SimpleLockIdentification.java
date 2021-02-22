package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockIdentification;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockRuntime;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 22:28
 */
public class SimpleLockIdentification implements LockIdentification {

    public static final String SPLITTER = "#";

    @Override
    public String identify(LockMetadata lockMetadata, LockRuntime lockRuntime) {
        String key = lockMetadata.key.isEmpty() ? lockMetadata.method.getName() : lockMetadata.key;
        String prefix = lockMetadata.prefix.isEmpty() ? lockMetadata.method.getDeclaringClass().getName() : lockMetadata.prefix;
        return prefix + SPLITTER + key;
    }

}
