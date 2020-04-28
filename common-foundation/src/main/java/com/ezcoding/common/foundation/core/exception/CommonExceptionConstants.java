package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;

import static com.ezcoding.common.foundation.core.exception.ModuleConstants.DEFAULT_FUNCTION_NAME;
import static com.ezcoding.common.foundation.core.exception.ModuleConstants.DEFAULT_MODULE_LAYER_MODULE;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-01 21:01
 */
public class CommonExceptionConstants {

    public static final FunctionLayerModule COMMON_PARAM_VALIDATE_ERROR = new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, DEFAULT_FUNCTION_NAME, "1");
    public static final FunctionLayerModule COMMON_REQUEST_TYPE_ERROR = new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, DEFAULT_FUNCTION_NAME, "2");
    public static final FunctionLayerModule COMMON_RESOURCE_NOT_FIND_ERROR = new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, DEFAULT_FUNCTION_NAME, "3");
    public static final FunctionLayerModule COMMON_USER_NOT_LOGIN_ERROR = new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, DEFAULT_FUNCTION_NAME, "4");
    public static final FunctionLayerModule COMMON_NO_PERMISSION_ERROR = new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, DEFAULT_FUNCTION_NAME, "5");
    public static final FunctionLayerModule COMMON_PARAM_PARSE_ERROR = new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, DEFAULT_FUNCTION_NAME, "6");
    public static final FunctionLayerModule COMMON_TOKEN_PARSE_ERROR = new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, DEFAULT_FUNCTION_NAME, "7");
    public static final FunctionLayerModule COMMON_SERVICE_NOT_AVALIABLE_ERROR = new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, DEFAULT_FUNCTION_NAME, "8");
    public static final FunctionLayerModule COMMON_USER_IS_KICKED_OUT_ERROR = new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, DEFAULT_FUNCTION_NAME, "9");
    public static final FunctionLayerModule COMMON_REMOTE_REQUEST_ERROR = new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, DEFAULT_FUNCTION_NAME, "10");

}
