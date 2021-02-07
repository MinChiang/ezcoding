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
     * @param lockRuntime  锁运行时参数
     * @return 锁结果
     * @throws Exception 异常
     */
    LockResult lock(String lockKey, LockMetadata lockMetadata, LockRuntime lockRuntime) throws Exception;

    /**
     * 获取锁
     *
     * @param lockKey      锁对象
     * @param lockMetadata 锁元数据
     * @param lockRuntime  锁运行时参数
     */
    void unlock(String lockKey, LockMetadata lockMetadata, LockRuntime lockRuntime);

}
