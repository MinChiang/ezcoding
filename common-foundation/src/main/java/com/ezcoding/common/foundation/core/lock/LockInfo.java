package com.ezcoding.common.foundation.core.lock;

import java.lang.reflect.Method;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 11:16
 */
public class LockInfo {

    private final Object object;
    private final Method method;
    private final Object[] args;

    public LockInfo(Object object, Method method, Object[] args) {
        this.object = object;
        this.method = method;
        this.args = args;
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

}
