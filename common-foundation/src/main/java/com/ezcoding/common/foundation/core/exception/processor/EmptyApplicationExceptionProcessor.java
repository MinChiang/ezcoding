package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.ApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-17 11:16
 */
public class EmptyApplicationExceptionProcessor extends AbstractLayerModuleProcessor {

    @Override
    public ProcessContext process(ApplicationException applicationException, ProcessContext processContext) {
        processContext.setProcessed(true);
        return processContext;
    }

}
