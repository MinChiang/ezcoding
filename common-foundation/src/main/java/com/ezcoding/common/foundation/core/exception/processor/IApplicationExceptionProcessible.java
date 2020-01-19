package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.ApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-17 9:11
 */
public interface IApplicationExceptionProcessible {

    /**
     * 判断错误是否能被捕获处理
     *
     * @param applicationException 程序错误
     * @return 是否能够捕获处理
     */
    boolean canProcessible(ApplicationException applicationException);

    /**
     * 处理错误
     *
     * @param applicationException 程序错误
     * @return 处理结果上下文
     */
    ProcessContext process(ApplicationException applicationException);

}
