package com.ezcoding.common.constant;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.constant.GlobalConstants;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-03-23 9:10
 */
public class UserModuleConstants {

    public static final String ACCOUNT_COMMON_MODEUL_NAME = "common";
    public static final String ACCOUNT_USER_MODEUL_NAME = "user";
    public static final String ACCOUNT_MANAGEMENT_MODEUL_NAME = "management";

    public static final int ACCOUNT_COMMON_MODULE_CODE = 0;
    public static final int ACCOUNT_CCOUNT_MODULE_CODE = 1;
    public static final int ACCOUNT_MANAGEMENT_MODULE_CODE = 2;

    public static final ApplicationLayerModule ACCOUNT_APPLICATION_LAYER_MODULE = new ApplicationLayerModule(GlobalConstants.Application.USER, String.valueOf(GlobalConstants.Application.USER_APPLICATION_CODE));

    public static final ModuleLayerModule ACCOUNT_COMMON_MODULE_LAYER_MODULE = new ModuleLayerModule(ACCOUNT_APPLICATION_LAYER_MODULE, ACCOUNT_COMMON_MODEUL_NAME, String.valueOf(ACCOUNT_COMMON_MODULE_CODE));
    public static final ModuleLayerModule ACCOUNT_USER_MODULE_LAYER_MODULE = new ModuleLayerModule(ACCOUNT_APPLICATION_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, String.valueOf(ACCOUNT_CCOUNT_MODULE_CODE));
    public static final ModuleLayerModule ACCOUNT_MANAGEMENT_MODULE_LAYER_MODULE = new ModuleLayerModule(ACCOUNT_APPLICATION_LAYER_MODULE, ACCOUNT_MANAGEMENT_MODEUL_NAME, String.valueOf(ACCOUNT_MANAGEMENT_MODULE_CODE));

}
