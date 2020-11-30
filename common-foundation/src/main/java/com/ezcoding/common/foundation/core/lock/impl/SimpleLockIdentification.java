package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockIdentification;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockProcessor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 22:28
 */
public class SimpleLockIdentification implements LockIdentification {

    private Map<String, LockProcessor> map = new ConcurrentHashMap<>();

    @Override
    public LockProcessor find(LockMetadata lockMetadata) {

        return null;
    }

}
