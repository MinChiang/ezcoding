package com.ezcoding.common.foundation.core.exception;

import static com.ezcoding.common.foundation.core.exception.ModuleConstants.DEFAULT_MODULE_LAYER_MODULE;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-01 21:01
 */
public interface ExceptionCodeGeneratorConstants {

    WebExceptionCodeGenerator GEN_COMMON_PARAM_VALIDATE_ERROR = new WebExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, 1, "COMMON_PARAM_VALIDATE_ERROR");
    WebExceptionCodeGenerator GEN_COMMON_REQUEST_TYPE_ERROR = new WebExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, 2, "COMMON_REQUEST_TYPE_ERROR");
    WebExceptionCodeGenerator GEN_COMMON_RESOURCE_NOT_FIND_ERROR = new WebExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, 3, "COMMON_RESOURCE_NOT_FIND_ERROR");
    WebExceptionCodeGenerator GEN_COMMON_USER_NOT_LOGIN_ERROR = new WebExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, 4, "COMMON_USER_NOT_LOGIN_ERROR");
    WebExceptionCodeGenerator GEN_COMMON_NO_PERMISSION_ERROR = new WebExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, 5, "COMMON_NO_PERMISSION_ERROR");
    WebExceptionCodeGenerator GEN_COMMON_PARAM_PARSE_ERROR = new WebExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, 6, "COMMON_PARAM_PARSE_ERROR");
    WebExceptionCodeGenerator GEN_COMMON_TOKEN_PARSE_ERROR = new WebExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, 7, "COMMON_TOKEN_PARSE_ERROR");
    WebExceptionCodeGenerator GEN_COMMON_SERVICE_NOT_AVALIABLE_ERROR = new WebExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, 8, "COMMON_SERVICE_NOT_AVALIABLE_ERROR");
    WebExceptionCodeGenerator GEN_COMMON_USER_IS_KICKED_OUT_ERROR = new WebExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, 9, "COMMON_USER_IS_KICKED_OUT_ERROR");
    WebExceptionCodeGenerator GEN_COMMON_REMOTE_REQUEST_ERROR = new WebExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, 10, "COMMON_REMOTE_REQUEST_ERROR");
    WebExceptionCodeGenerator GEN_COMMON_LOCK_FAIL_ERROR = new WebExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, 11, "COMMON_LOCK_FAIL_ERROR");

}
