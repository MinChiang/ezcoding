package com.ezcoding.common.foundation.core.lock;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-02-07 11:26
 */
public class LockRuntime {

    public final Object target;
    public final Object[] args;
    public final LockContext lockContext;

    public LockRuntime(Object target, Object[] args, LockContext lockContext) {
        this.target = target;
        this.args = args;
        this.lockContext = lockContext;
    }

}
