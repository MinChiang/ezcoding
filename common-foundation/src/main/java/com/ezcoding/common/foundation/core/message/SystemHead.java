package com.ezcoding.common.foundation.core.message;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-07 15:28
 */
public interface SystemHead {

    /**
     * 获取报文生成日期（时间戳）
     *
     * @return 报文生成日期
     */
    long getTransactionDate();

    /**
     * 获取报文版本号
     *
     * @return 报文版本号
     */
    String getVersion();

}
