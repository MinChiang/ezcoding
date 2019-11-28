package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-14 17:06
 */
public class CommonApplicationException extends AbstractApplicationException {

    public static final String COMMON_PARAM_VALIDATE_ERROR = "COMMON_PARAM_VALIDATE_ERROR";
    public static final String COMMON_REQUEST_TYPE_ERROR = "COMMON_REQUEST_TYPE_ERROR";
    public static final String COMMON_RESOURCE_NOT_FIND_ERROR = "COMMON_RESOURCE_NOT_FIND_ERROR";
    public static final String COMMON_USER_NOT_LOGIN_ERROR = "COMMON_USER_NOT_LOGIN_ERROR";
    public static final String COMMON_NO_PERMISSION_ERROR = "COMMON_NO_PERMISSION_ERROR";
    public static final String COMMON_PARAM_PARSE_ERROR = "COMMON_PARAM_PARSE_ERROR";
    public static final String COMMON_TOKEN_PARSE_ERROR = "COMMON_TOKEN_PARSE_ERROR";
    public static final String COMMON_SERVICE_NOT_AVALIABLE_ERROR = "COMMON_SERVICE_NOT_AVALIABLE_ERROR";
    public static final String COMMON_USER_IS_KICKED_OUT_ERROR = "COMMON_USER_IS_KICKED_OUT_ERROR";

    CommonApplicationException(ModuleLayerModule moduleLayerModule, String detailCode, String message, Throwable cause) {
        super(moduleLayerModule, detailCode, message, cause);
    }

}
