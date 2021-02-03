package com.ezcoding.common.foundation.core.lock;

/**
 * 上锁失败异常
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-02-03 17:28
 */
public class LockFailException extends Exception {

    public LockFailException(String message) {
        super(message);
    }

    public LockFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockFailException(Throwable cause) {
        super(cause);
    }

    protected LockFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
