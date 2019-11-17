package com.ezcoding.facility.module.area.exception;

import com.ezcoding.facility.common.constant.FacilityModuleExceptionConstants;
import com.ezcoding.facility.common.exception.AbstractFacilityApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-22 16:32
 */
public class AreaException extends AbstractFacilityApplicationException {

    AreaException(String detailCode, String message, Throwable cause) {
        super(FacilityModuleExceptionConstants.AREA_MODULE, detailCode, message, cause);
    }

}
