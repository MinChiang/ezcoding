package com.ezcoding.auth.common.exception;

import com.ezcoding.common.foundation.core.exception.AbstractApplicationException;
import com.ezcoding.common.foundation.util.ApplicationUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-06-18 15:04
 */
public abstract class AbstractAuthApplicationException extends AbstractApplicationException {

    public AbstractAuthApplicationException(String moduleCode, String detailCode, String message, Throwable cause) {
        super(ApplicationUtils.getApplicationMetadata().getCategoryFormat(), moduleCode, detailCode, message, cause);
    }

}
