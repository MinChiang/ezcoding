package com.ezcoding.common.foundation.core.lock;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-04 9:38
 */
public class LockResult {

    private final boolean success;
    private final String lockKey;

    private LockResult(boolean success, String lockKey) {
        this.success = success;
        this.lockKey = lockKey;
    }

    /**
     * 加锁成功
     *
     * @param lockKey 锁名称
     */
    public static LockResult lockSuccess(String lockKey) {
        return new LockResult(true, lockKey);
    }

    /**
     * 加锁失败
     */
    public static LockResult lockFail() {
        return new LockResult(false, null);
    }

    /**
     * 判断上锁成功
     *
     * @return 上锁是否成功
     */
    public boolean success() {
        return success;
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
