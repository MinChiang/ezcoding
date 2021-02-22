package com.ezcoding.common.foundation.core.lock;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 9:47
 */
public class LockMetadata extends LockInfo {

    public final String key;
    public final String prefix;
    public final long expireTime;
    public final long waitTime;
    public final TimeUnit timeUnit;
    public final Class<? extends LockImplement> implementClass;
    public final Class<? extends LockIdentification> identificationClass;
    public final String failMessage;

    public LockMetadata(Method method,
                        String[] parameterNames,
                        String key,
                        String prefix,
                        long expireTime,
                        long waitTime,
                        TimeUnit timeUnit,
                        Class<? extends LockImplement> implementClass,
                        Class<? extends LockIdentification> identificationClass,
                        String failMessage) {
        super(method, parameterNames);
        this.key = key;
        this.prefix = prefix;
        this.expireTime = expireTime;
        this.waitTime = waitTime;
        this.timeUnit = timeUnit;
        this.implementClass = implementClass;
        this.identificationClass = identificationClass;
        this.failMessage = failMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        LockMetadata that = (LockMetadata) o;
        return expireTime == that.expireTime && waitTime == that.waitTime && key.equals(that.key) && prefix.equals(that.prefix) && timeUnit == that.timeUnit && implementClass.equals(that.implementClass) && identificationClass.equals(that.identificationClass) && failMessage.equals(that.failMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, prefix, expireTime, waitTime, timeUnit, implementClass, identificationClass, failMessage);
    }

}
