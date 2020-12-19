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

    private final Method method;
    private final LockConfig lockConfig;

    private final LockMetadata lockMetadata;
    private final LockIdentification lockIdentification;
    private final LockImplement lockImplement;

    LockProcessor(LockConfig lockConfig,
                  Method method) {
        this.method = method;
        this.lockConfig = lockConfig;

        //元素据初始化
        StandardLock standardLock = this.method.getAnnotation(StandardLock.class);
        this.lockMetadata = new LockMetadata(
                standardLock.key(),
                standardLock.prefix(),
                standardLock.expireTime(),
                standardLock.waitTime(),
                standardLock.timeUnit(),
                standardLock.processorClass(),
                standardLock.identificationClass()
        );
        this.lockIdentification = lockConfig.acquireLockIdentification(this.lockMetadata.identificationClass);
        this.lockImplement = lockConfig.acquireLockImplement(this.lockMetadata.implementClass);
    }

    /**
     * 上锁
     *
     * @param target 目标对象
     * @param args   入参
     * @return 锁元素
     */
    public LockResult lock(Object target, Object[] args) throws Exception {
        String lockKey = lockIdentification.identify(lockMetadata, lockConfig, this.method);
        if (lockKey == null || lockKey.isEmpty()) {
            return LockResult.lockFail();
        }
        return lockImplement.lock(lockKey, this.lockMetadata, target, args);
    }

    /**
     * 解锁
     *
     * @param lockKey 锁标志
     * @param target  目标对象
     * @param args    入参
     */
    public void unlock(String lockKey, Object target, Object[] args) {
        lockImplement.unlock(lockKey, this.lockMetadata, target, args);
    }

    public LockConfig getLockConfig() {
        return lockConfig;
    }

    public Method getMethod() {
        return method;
    }

    public LockMetadata getLockMetadata() {
        return lockMetadata;
    }

    public LockIdentification getLockIdentification() {
        return lockIdentification;
    }

    public LockImplement getLockImplement() {
        return lockImplement;
    }

}
