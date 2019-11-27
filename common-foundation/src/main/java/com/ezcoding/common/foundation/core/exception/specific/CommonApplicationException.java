package com.ezcoding.common.foundation.core.exception.specific;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.exception.AbstractApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-14 17:06
 */
public class CommonApplicationException extends AbstractApplicationException {

    CommonApplicationException(ModuleLayerModule moduleLayerModule, String detailCode, String message, Throwable cause) {
        super(moduleLayerModule, detailCode, message, cause);
    }

}
