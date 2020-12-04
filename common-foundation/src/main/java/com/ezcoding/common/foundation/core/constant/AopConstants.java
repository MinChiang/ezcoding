package com.ezcoding.common.foundation.core.constant;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-04 11:21
 */
public interface AopConstants {

    interface Order {

        /**
         * 日志打印
         */
        int LOG_ORDER = 0;

        /**
         * 锁
         */
        int LOCK_ORDER = 100;

        /**
         * 事务
         */
        int TRANSACTION_ORDER = 200;

    }

}
