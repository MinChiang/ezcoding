package com.ezcoding.common.foundation.core.exception;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-28 21:12
 */
public interface IExceptionBuilder extends IApplicationIdentifiable {

    /**
     * 构建异常
     *
     * @param cause 错误原因
     * @return 异常实例
     */
    ApplicationException build(Throwable cause);

    /**
     * 构建异常
     *
     * @return 异常实例
     */
    ApplicationException build();

}
