package com.ezcoding.common.foundation.core.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 9:47
 */
public class LockMetadata {

    public final String key;
    public final String prefix;
    public final long expireTime;
    public final long waitTime;
    public final TimeUnit timeUnit;
    public final Class<? extends LockImplement> implementClass;
    public final Class<? extends LockIdentification> identificationClass;

    public LockMetadata(String key, String prefix, long expireTime, long waitTime, TimeUnit timeUnit, Class<? extends LockImplement> implementClass, Class<? extends LockIdentification> identificationClass) {
        this.key = key;
        this.prefix = prefix;
        this.expireTime = expireTime;
        this.waitTime = waitTime;
        this.timeUnit = timeUnit;
        this.implementClass = implementClass;
        this.identificationClass = identificationClass;
    }

}
