package com.ezcoding.common.foundation.core.lock;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-02-22 20:21
 */
public class LockInfo {

    public final Method method;

    public final String[] parameterNames;

    public LockInfo(Method method, String[] parameterNames) {
        this.method = method;
        this.parameterNames = parameterNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LockInfo lockInfo = (LockInfo) o;
        return method.equals(lockInfo.method) && Arrays.equals(parameterNames, lockInfo.parameterNames);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(method);
        result = 31 * result + Arrays.hashCode(parameterNames);
        return result;
    }

}
