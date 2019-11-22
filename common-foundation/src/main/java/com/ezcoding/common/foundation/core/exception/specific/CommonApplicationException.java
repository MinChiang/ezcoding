package com.ezcoding.common.foundation.core.exception.specific;

import com.ezcoding.common.foundation.core.exception.AbstractApplicationException;
import com.ezcoding.common.foundation.util.ApplicationUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-14 17:06
 */
public class CommonApplicationException extends AbstractApplicationException {

    public static final String APPLICATION_MODULE_CODE = "0000";

    CommonApplicationException(String detailCode, String message, Throwable cause) {
        super(ApplicationUtils.getApplicationMetadata().getCategoryFormat(), APPLICATION_MODULE_CODE, detailCode, message, cause);
    }

}
