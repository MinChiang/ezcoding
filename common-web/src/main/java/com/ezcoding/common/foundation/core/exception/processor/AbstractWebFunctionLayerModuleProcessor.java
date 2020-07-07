package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.ApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-02-10 16:41
 */
public abstract class AbstractWebFunctionLayerModuleProcessor extends AbstractErrorSuffixCodeProcessor {

    /**
     * 执行对应的处理步骤
     *
     * @param applicationException 程序错误
     * @param processContext       处理上下文
     */
    public abstract void doProcess(ApplicationException applicationException, WebProcessContext processContext);

    @Override
    public ProcessContext process(ApplicationException applicationException, ProcessContext processContext) {
        doProcess(applicationException, (WebProcessContext) processContext);
        processContext.setProcessed(true);
        return processContext;
    }

}
