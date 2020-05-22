package com.ezcoding.common.foundation.core.exception;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:46
 */
public interface ApplicationIdentifiable {

    /**
     * 获取完整的错误码
     *
     * @return 完整的错误码
     */
    String getIdentification();

    /**
     * 获取错误摘要
     *
     * @return 错误摘要信息
     */
    String getSummary();

}
