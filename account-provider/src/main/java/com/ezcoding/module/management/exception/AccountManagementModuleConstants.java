package com.ezcoding.module.management.exception;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;

import static com.ezcoding.common.constant.UserModuleConstants.ACCOUNT_MANAGEMENT_MODEUL_NAME;
import static com.ezcoding.common.constant.UserModuleConstants.ACCOUNT_MANAGEMENT_MODULE_LAYER_MODULE;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-03-23 10:14
 */
public class AccountManagementModuleConstants {

    public static final FunctionLayerModule MANAGEMENT_PARENT_DEPARTMENT_NOT_FOUND = new FunctionLayerModule(ACCOUNT_MANAGEMENT_MODULE_LAYER_MODULE, ACCOUNT_MANAGEMENT_MODEUL_NAME, "0");
    public static final FunctionLayerModule MANAGEMENT_GROUP_NOT_VALID = new FunctionLayerModule(ACCOUNT_MANAGEMENT_MODULE_LAYER_MODULE, ACCOUNT_MANAGEMENT_MODEUL_NAME, "1");
    public static final FunctionLayerModule MANAGEMENT_ROLES_NOT_VALID = new FunctionLayerModule(ACCOUNT_MANAGEMENT_MODULE_LAYER_MODULE, ACCOUNT_MANAGEMENT_MODEUL_NAME, "2");

}


