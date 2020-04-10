package com.ezcoding.module.management.exception;

import com.ezcoding.common.foundation.core.exception.WebExceptionCodeGenerator;

import static com.ezcoding.module.management.exception.AccountManagementModuleConstants.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-14 15:57
 */
public class AccountManagementExceptionConstants {

    public static final WebExceptionCodeGenerator GEN_MANAGEMENT_PARENT_DEPARTMENT_NOT_FOUND = new WebExceptionCodeGenerator(MANAGEMENT_PARENT_DEPARTMENT_NOT_FOUND, "PARENT_DEPARTMENT_NOT_FOUND");
    public static final WebExceptionCodeGenerator GEN_MANAGEMENT_GROUP_NOT_VALID = new WebExceptionCodeGenerator(MANAGEMENT_GROUP_NOT_VALID, "GROUP_NOT_VALID");
    public static final WebExceptionCodeGenerator GEN_MANAGEMENT_ROLES_NOT_VALID = new WebExceptionCodeGenerator(MANAGEMENT_ROLES_NOT_VALID, "ROLES_NOT_VALID");

}
