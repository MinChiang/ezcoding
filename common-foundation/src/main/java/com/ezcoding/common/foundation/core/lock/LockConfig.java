package com.ezcoding.common.foundation.core.lock;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:46
 */
public class LockConfig {

    private final Map<Class<? extends LockImplement>, LockImplement> lockImplementMap;
    private final Map<Class<? extends LockIdentification>, LockIdentification> lockIdentificationMap;

    public LockConfig(Map<Class<? extends LockImplement>, LockImplement> lockImplementMap,
                      Map<Class<? extends LockIdentification>, LockIdentification> lockIdentificationMap) {
        this.lockImplementMap = lockImplementMap;
        this.lockIdentificationMap = lockIdentificationMap;
    }

    /**
     * 获取锁实现类
     *
     * @param cls 锁实现类
     * @return 锁实现实例
     */
    public LockImplement acquireLockImplement(Class<? extends LockImplement> cls) {
        return lockImplementMap.get(cls);
    }

    /**
     * 获取锁id
     *
     * @param cls 锁id实现类
     * @return 锁id实例
     */
    public LockIdentification acquireLockIdentification(Class<? extends LockIdentification> cls) {
        return lockIdentificationMap.get(cls);
    }

}
