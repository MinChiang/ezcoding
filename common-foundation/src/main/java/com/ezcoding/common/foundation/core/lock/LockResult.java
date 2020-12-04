package com.ezcoding.common.foundation.core.lock;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-04 9:38
 */
public class LockResult {

    private final boolean lock;
    private final String lockKey;

    /**
     * 加锁成功
     *
     * @param lockKey 锁名称
     */
    public LockResult(String lockKey) {
        this.lock = true;
        this.lockKey = lockKey;
    }

    /**
     * 加锁失败
     */
    public LockResult() {
        this.lock = false;
        this.lockKey = null;
    }

    /**
     * 判断上锁成功
     *
     * @return 上锁是否成功
     */
    public boolean success() {
        return lock;
    }

    /**
     * 获取锁标志
     *
     * @return 锁标志
     */
    public String acquireKey() {
        return this.lockKey;
    }

}
