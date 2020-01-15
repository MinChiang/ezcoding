package com.ezcoding.common.foundation.core.exception;

import static com.ezcoding.common.foundation.core.exception.ModuleConstants.DEFAULT_MODULE_LAYER_MODULE;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-01 21:01
 */
public class ExceptionCodeGeneratorConstants {

    public static final TemplateExceptionCodeGenerator COMMON_PARAM_VALIDATE_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "1", "COMMON_PARAM_VALIDATE_ERROR");
    public static final TemplateExceptionCodeGenerator COMMON_REQUEST_TYPE_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "2", "COMMON_REQUEST_TYPE_ERROR");
    public static final TemplateExceptionCodeGenerator COMMON_RESOURCE_NOT_FIND_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "3", "COMMON_RESOURCE_NOT_FIND_ERROR");
    public static final TemplateExceptionCodeGenerator COMMON_USER_NOT_LOGIN_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "4", "COMMON_USER_NOT_LOGIN_ERROR");
    public static final TemplateExceptionCodeGenerator COMMON_NO_PERMISSION_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "5", "COMMON_NO_PERMISSION_ERROR");
    public static final TemplateExceptionCodeGenerator COMMON_PARAM_PARSE_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "6", "COMMON_PARAM_PARSE_ERROR");
    public static final TemplateExceptionCodeGenerator COMMON_TOKEN_PARSE_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "7", "COMMON_TOKEN_PARSE_ERROR");
    public static final TemplateExceptionCodeGenerator COMMON_SERVICE_NOT_AVALIABLE_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "8", "COMMON_SERVICE_NOT_AVALIABLE_ERROR");
    public static final TemplateExceptionCodeGenerator COMMON_USER_IS_KICKED_OUT_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(DEFAULT_MODULE_LAYER_MODULE, "9", "COMMON_USER_IS_KICKED_OUT_ERROR");

}
