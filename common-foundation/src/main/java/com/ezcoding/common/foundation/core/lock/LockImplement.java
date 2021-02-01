package com.ezcoding.common.foundation.core.lock;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 9:41
 */
public interface LockImplement {

    /**
     * 获取锁
     *
     * @param lockKey      锁对象
     * @param lockMetadata 锁元数据
     * @param target       目标对象
     * @param args         参数值
     * @return 锁结果
     * @throws Exception 异常
     */
    LockResult lock(String lockKey, LockMetadata lockMetadata, Object target, Object[] args) throws Exception;

    /**
     * 获取锁
     *
     * @param lockKey      锁对象
     * @param lockMetadata 锁元数据
     * @param target       目标对象
     * @param args         参数值
     */
    void unlock(String lockKey, LockMetadata lockMetadata, Object target, Object[] args);

}
