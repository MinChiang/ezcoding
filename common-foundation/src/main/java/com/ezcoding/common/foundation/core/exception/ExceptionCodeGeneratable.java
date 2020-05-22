package com.ezcoding.common.foundation.core.exception;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-28 21:33
 */
public interface ExceptionCodeGeneratable {

    /**
     * 产生错误唯一标识
     *
     * @return 错误唯一标识
     */
    String generate();

}
