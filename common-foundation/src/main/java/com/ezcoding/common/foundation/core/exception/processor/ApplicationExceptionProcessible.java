package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.application.ModuleNameable;
import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-17 9:11
 */
public interface ApplicationExceptionProcessible {

    int APPLICATION_BEGIN = ModuleNameable.APPLICATION_CODE_LENGTH;
    int MODULE_BEGIN = APPLICATION_BEGIN + ModuleNameable.MODULE_CODE_LENGTH;
    int ERROR_CODE_END = MODULE_BEGIN + ExceptionCodeGeneratable.ERROR_SUFFIX_CODE_LENGTH;

    /**
     * 判断错误是否能被捕获处理
     *
     * @param applicationException 程序错误
     * @return 是否能够捕获处理
     */
    boolean canProcess(ApplicationException applicationException);

    /**
     * 处理错误
     *
     * @param applicationException 程序错误
     * @param processContext       处理上下文
     * @return 处理结果上下文
     */
    ProcessContext process(ApplicationException applicationException, ProcessContext processContext);

}
