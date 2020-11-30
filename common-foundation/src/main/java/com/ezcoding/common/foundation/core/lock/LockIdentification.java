package com.ezcoding.common.foundation.core.lock;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 22:05
 */
public interface LockIdentification {

    /**
     * 获取锁处理对象
     *
     * @param lockMetadata 元数据
     * @return 锁处理对象
     */
    LockProcessor find(LockMetadata lockMetadata);

}
