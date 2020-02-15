package com.ezcoding.common.foundation.core.exception;

import static com.ezcoding.common.foundation.core.exception.CommonExceptionConstants.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-01 21:01
 */
public class ExceptionCodeGeneratorConstants {

    public static final TemplateExceptionCodeGenerator GEN_COMMON_PARAM_VALIDATE_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(COMMON_PARAM_VALIDATE_ERROR, "COMMON_PARAM_VALIDATE_ERROR");
    public static final TemplateExceptionCodeGenerator GEN_COMMON_REQUEST_TYPE_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(COMMON_REQUEST_TYPE_ERROR, "COMMON_REQUEST_TYPE_ERROR");
    public static final TemplateExceptionCodeGenerator GEN_COMMON_RESOURCE_NOT_FIND_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(COMMON_RESOURCE_NOT_FIND_ERROR, "COMMON_RESOURCE_NOT_FIND_ERROR");
    public static final TemplateExceptionCodeGenerator GEN_COMMON_USER_NOT_LOGIN_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(COMMON_USER_NOT_LOGIN_ERROR, "COMMON_USER_NOT_LOGIN_ERROR");
    public static final TemplateExceptionCodeGenerator GEN_COMMON_NO_PERMISSION_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(COMMON_NO_PERMISSION_ERROR, "COMMON_NO_PERMISSION_ERROR");
    public static final TemplateExceptionCodeGenerator GEN_COMMON_PARAM_PARSE_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(COMMON_PARAM_PARSE_ERROR, "COMMON_PARAM_PARSE_ERROR");
    public static final TemplateExceptionCodeGenerator GEN_COMMON_TOKEN_PARSE_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(COMMON_TOKEN_PARSE_ERROR, "COMMON_TOKEN_PARSE_ERROR");
    public static final TemplateExceptionCodeGenerator GEN_COMMON_SERVICE_NOT_AVALIABLE_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(COMMON_SERVICE_NOT_AVALIABLE_ERROR, "COMMON_SERVICE_NOT_AVALIABLE_ERROR");
    public static final TemplateExceptionCodeGenerator GEN_COMMON_USER_IS_KICKED_OUT_ERROR = ExceptionCodeGeneratorFactory.templateExceptionCodeGenerator(COMMON_USER_IS_KICKED_OUT_ERROR, "COMMON_USER_IS_KICKED_OUT_ERROR");

}
