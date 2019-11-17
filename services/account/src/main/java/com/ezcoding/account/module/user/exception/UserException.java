package com.ezcoding.account.module.user.exception;

import com.ezcoding.account.common.constant.UserModuleExceptionConstants;
import com.ezcoding.account.common.exception.AbstractAccountApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-14 15:22
 */
public class UserException extends AbstractAccountApplicationException {

    UserException(String detailCode, String message, Throwable cause) {
        super(UserModuleExceptionConstants.USER_MODULE, detailCode, message, cause);
    }

}
