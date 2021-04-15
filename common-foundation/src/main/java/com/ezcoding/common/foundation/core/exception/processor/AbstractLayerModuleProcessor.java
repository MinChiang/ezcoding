package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.ApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-17 10:27
 */
public abstract class AbstractLayerModuleProcessor implements ApplicationExceptionProcessible {

    @Override
    public boolean canProcess(ApplicationException applicationException) {
        return true;
    }

}
