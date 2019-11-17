package com.ezcoding.facility.module.message.exception;

import com.ezcoding.facility.common.constant.FacilityModuleExceptionConstants;
import com.ezcoding.facility.common.exception.AbstractFacilityApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-12 16:57
 */
public class MessageException extends AbstractFacilityApplicationException {

    MessageException(String detailCode, String message, Throwable cause) {
        super(FacilityModuleExceptionConstants.MESSAGE_MODULE, detailCode, message, cause);
    }

}
