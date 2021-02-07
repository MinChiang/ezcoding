package com.ezcoding.common.foundation.core.lock;

import java.lang.reflect.Method;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-02-07 11:26
 */
public class LockRuntime {

    public final Object target;
    public final Object[] args;
    public final Method method;
    public final String[] parameterNames;
    public final LockContext lockContext;

    public LockRuntime(Object target, Object[] args, Method method, String[] parameterNames, LockContext lockContext) {
        this.target = target;
        this.args = args;
        this.method = method;
        this.parameterNames = parameterNames;
        this.lockContext = lockContext;
    }

}
