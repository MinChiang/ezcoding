package com.ezcoding.starter.foundation.core.exception;

import com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorFactory;
import com.ezcoding.common.foundation.core.exception.IExceptionCodeGeneratable;

import static com.ezcoding.starter.foundation.core.exception.ModuleConstants.DEFAULT_MODULE_LAYER_MODULE;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-01 21:01
 */
public class ExceptionCodeGeneratorConstants {

    public static final IExceptionCodeGeneratable COMMON_PARAM_VALIDATE_ERROR = ExceptionCodeGeneratorFactory.moduleExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "1");
    public static final IExceptionCodeGeneratable COMMON_REQUEST_TYPE_ERROR = ExceptionCodeGeneratorFactory.moduleExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "2");
    public static final IExceptionCodeGeneratable COMMON_RESOURCE_NOT_FIND_ERROR = ExceptionCodeGeneratorFactory.moduleExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "3");
    public static final IExceptionCodeGeneratable COMMON_USER_NOT_LOGIN_ERROR = ExceptionCodeGeneratorFactory.moduleExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "4");
    public static final IExceptionCodeGeneratable COMMON_NO_PERMISSION_ERROR = ExceptionCodeGeneratorFactory.moduleExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "5");
    public static final IExceptionCodeGeneratable COMMON_PARAM_PARSE_ERROR = ExceptionCodeGeneratorFactory.moduleExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "6");
    public static final IExceptionCodeGeneratable COMMON_TOKEN_PARSE_ERROR = ExceptionCodeGeneratorFactory.moduleExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "7");
    public static final IExceptionCodeGeneratable COMMON_SERVICE_NOT_AVALIABLE_ERROR = ExceptionCodeGeneratorFactory.moduleExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "8");
    public static final IExceptionCodeGeneratable COMMON_USER_IS_KICKED_OUT_ERROR = ExceptionCodeGeneratorFactory.moduleExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "9");

}
