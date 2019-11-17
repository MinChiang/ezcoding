package com.ezcoding.auth.module.resource.exception;

import com.ezcoding.auth.common.constant.AuthModuleExceptionConstants;
import com.ezcoding.auth.common.exception.AbstractAuthApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-06-18 17:27:52
 */
public class ResourceException extends AbstractAuthApplicationException {

    ResourceException(String detailCode, String message, Throwable cause) {
        super(AuthModuleExceptionConstants.RESOURCE_MODULE, detailCode, message, cause);
    }

}