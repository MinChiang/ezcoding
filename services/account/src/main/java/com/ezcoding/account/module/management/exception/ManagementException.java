package com.ezcoding.account.module.management.exception;

import com.ezcoding.account.common.constant.UserModuleExceptionConstants;
import com.ezcoding.account.common.exception.AbstractAccountApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-14 15:22
 */
public class ManagementException extends AbstractAccountApplicationException {

    ManagementException(String detailCode, String message, Throwable cause) {
        super(UserModuleExceptionConstants.MANAGEMENT_MODULE, detailCode, message, cause);
    }

}
