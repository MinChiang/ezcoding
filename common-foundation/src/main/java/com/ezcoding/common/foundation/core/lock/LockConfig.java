package com.ezcoding.common.foundation.core.lock;

import com.ezcoding.common.foundation.core.lock.impl.DefaultLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.DefaultLockImplement;
import com.ezcoding.common.foundation.core.lock.impl.SimpleLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.SimpleLockImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:46
 */
public class LockConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockConfig.class);

    private final Map<Class<? extends LockImplement>, LockImplement> lockImplementMap = new ConcurrentHashMap<>();
    private final Map<Class<? extends LockIdentification>, LockIdentification> lockIdentificationMap = new ConcurrentHashMap<>();

    private LockImplement defaultLockImplement = new SimpleLockImplement();
    private LockIdentification defaultLockIdentification = new SimpleLockIdentification();

    /**
     * 获取锁实现类
     *
     * @param cls 锁实现类
     * @return 锁实现实例
     */
    public LockImplement acquireLockImplement(Class<? extends LockImplement> cls) {
        LockImplement lockImplement = lockImplementMap.computeIfAbsent(cls, key -> getInstance(key, defaultLockImplement));
        if (lockImplement instanceof DefaultLockImplement) {
            return ((DefaultLockImplement) lockImplement).getLock();
        }
        return lockImplement;
    }

    /**
     * 获取锁id
     *
     * @param cls 锁id实现类
     * @return 锁id实例
     */
    public LockIdentification acquireLockIdentification(Class<? extends LockIdentification> cls) {
        LockIdentification lockIdentification = lockIdentificationMap.computeIfAbsent(cls, key -> getInstance(key, defaultLockIdentification));
        if (lockIdentification instanceof DefaultLockIdentification) {
            return ((DefaultLockIdentification) lockIdentification).getDefaultLockIdentification();
        }
        return lockIdentification;
    }

    /**
     * 实例化
     *
     * @param cls             需要实例化类
     * @param <T>             类型
     * @param defaultInstance 默认取值
     * @return 实例
     */
    private <T> T getInstance(Class<? extends T> cls, T defaultInstance) {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("unable to find or instance class : {}", cls.getName());
        }
        return defaultInstance;
    }

    public void setDefaultLockImplement(LockImplement defaultLockImplement) {
        this.defaultLockImplement = defaultLockImplement;
    }

    public void setDefaultLockIdentification(LockIdentification defaultLockIdentification) {
        this.defaultLockIdentification = defaultLockIdentification;
    }

}
