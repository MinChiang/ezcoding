package com.ezcoding.common.foundation.core.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-01 17:43
 */
public class LockProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockProcessor.class);

    private final LockConfig lockConfig;
    private final Method method;

    LockProcessor(LockConfig lockConfig,
                  Method method) {
        this.method = method;
        this.lockConfig = lockConfig;
    }

    public void lock(LockRuntime lockRuntime) {

    }

    public void unlock(LockRuntime lockRuntime) {

    }

}
