package com.ezcoding.facility.module.cos.exception;

import com.ezcoding.facility.common.constant.FacilityModuleExceptionConstants;
import com.ezcoding.facility.common.exception.AbstractFacilityApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-26 10:12
 */
public class CosException extends AbstractFacilityApplicationException {

    CosException(String detailCode, String message, Throwable cause) {
        super(FacilityModuleExceptionConstants.COS_MODULE, detailCode, message, cause);
    }

}