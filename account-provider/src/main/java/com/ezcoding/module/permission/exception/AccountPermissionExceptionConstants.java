package com.ezcoding.module.permission.exception;

import com.ezcoding.common.foundation.core.exception.WebExceptionCodeGenerator;

import static com.ezcoding.module.permission.exception.AccountPermissionModuleConstants.PERMISSION_EXPRESSION_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-14 15:57
 */
public class AccountPermissionExceptionConstants {

    public static final WebExceptionCodeGenerator GEN_PERMISSION_EXPRESSION_ERROR = new WebExceptionCodeGenerator(PERMISSION_EXPRESSION_ERROR, "PERMISSION_EXPRESSION_ERROR");

}
