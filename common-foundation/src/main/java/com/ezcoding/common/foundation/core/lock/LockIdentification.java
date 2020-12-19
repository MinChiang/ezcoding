package com.ezcoding.common.foundation.core.lock;

import java.lang.reflect.Method;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 22:05
 */
public interface LockIdentification {

    /**
     * 获取锁处理对象
     *
     * @param lockMetadata 上锁信息
     * @param lockConfig
     * @param method
     * @return 锁处理对象
     */
    String identify(LockMetadata lockMetadata, LockConfig lockConfig, Method method);

}
