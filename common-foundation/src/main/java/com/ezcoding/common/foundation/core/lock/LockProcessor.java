package com.ezcoding.common.foundation.core.lock;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-30 9:41
 */
public interface LockProcessor {

    /**
     * 获取锁
     *
     * @param lockInfo     上锁信息
     * @param lockMetadata 锁元数据
     */
    boolean lock(LockInfo lockInfo, LockMetadata lockMetadata) throws Exception;

    /**
     * 获取锁
     *
     * @param lockInfo     上锁信息
     * @param lockMetadata 锁元数据
     */
    void unlock(LockInfo lockInfo, LockMetadata lockMetadata) throws Exception;

}
