package com.ezcoding.common.foundation.core.lock;

import java.lang.reflect.Method;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 11:16
 */
public class LockRuntime {

    private final Object target;
    private final Method method;
    private final Object[] args;

    public LockRuntime(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

}
