package com.ezcoding.facility.module.sms.exception;

import com.ezcoding.facility.common.constant.FacilityModuleExceptionConstants;
import com.ezcoding.facility.common.exception.AbstractFacilityApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-26 10:12
 */
public class SmsException extends AbstractFacilityApplicationException {

    SmsException(String detailCode, String message, Throwable cause) {
        super(FacilityModuleExceptionConstants.SMS_MODULE, detailCode, message, cause);
    }

}
