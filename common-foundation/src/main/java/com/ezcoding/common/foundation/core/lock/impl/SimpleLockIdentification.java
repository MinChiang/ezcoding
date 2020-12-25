package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockConfig;
import com.ezcoding.common.foundation.core.lock.LockIdentification;
import com.ezcoding.common.foundation.core.lock.LockMetadata;

import java.lang.reflect.Method;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 22:28
 */
public class SimpleLockIdentification implements LockIdentification {

    public static final String SPLITTER = "#";

    @Override
    public String identify(LockMetadata lockMetadata, LockConfig lockConfig, Method method) {
        String key = lockMetadata.key.isEmpty() ? method.getName() : lockMetadata.key;
        String prefix = lockMetadata.prefix.isEmpty() ? method.getName() : lockMetadata.prefix;
        return prefix + SPLITTER + key;
    }

}
