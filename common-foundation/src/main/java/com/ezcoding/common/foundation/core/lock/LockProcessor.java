package com.ezcoding.common.foundation.core.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-01 17:43
 */
public class LockProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockProcessor.class);

    public final LockRuntime lockRuntime;
    public final LockMetadata lockMetadata;
    public final LockIdentification lockIdentification;
    public final LockImplement lockImplement;

    LockProcessor(LockConfig lockConfig,
                  LockRuntime lockRuntime) {

        this.lockRuntime = lockRuntime;

        //元素据初始化
        StandardLock standardLock = this.lockRuntime.method.getAnnotation(StandardLock.class);
        this.lockMetadata = new LockMetadata(
                standardLock.key(),
                standardLock.prefix(),
                standardLock.expireTime(),
                standardLock.waitTime(),
                standardLock.timeUnit(),
                standardLock.implementClass(),
                standardLock.identificationClass(),
                standardLock.failMessage()
        );
        this.lockIdentification = lockConfig.acquireLockIdentification(this.lockMetadata.identificationClass);
        this.lockImplement = lockConfig.acquireLockImplement(this.lockMetadata.implementClass);
    }

    /**
     * 上锁
     *
     * @return 锁元素
     */
    public LockResult lock() throws Exception {
        String lockKey = lockIdentification.identify(lockMetadata, this.lockRuntime);
        if (lockKey == null || lockKey.isEmpty()) {
            return LockResult.lockFail();
        }
        LOGGER.debug("ready to lock key [{}]", lockKey);
        return lockImplement.lock(lockKey, this.lockMetadata, this.lockRuntime);
    }

    /**
     * 解锁
     *
     * @param lockKey 锁标志
     */
    public void unlock(String lockKey) {
        LOGGER.debug("ready to unlock key [{}]", lockKey);
        lockImplement.unlock(lockKey, this.lockMetadata, this.lockRuntime);
    }

}
