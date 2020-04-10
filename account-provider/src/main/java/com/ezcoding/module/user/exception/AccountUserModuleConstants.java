package com.ezcoding.module.user.exception;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;

import static com.ezcoding.common.constant.UserModuleConstants.ACCOUNT_USER_MODEUL_NAME;
import static com.ezcoding.common.constant.UserModuleConstants.ACCOUNT_USER_MODULE_LAYER_MODULE;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-03-23 10:14
 */
public class AccountUserModuleConstants {

    public static final FunctionLayerModule USER_EXIST_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "0");
    public static final FunctionLayerModule USER_NOT_EXIST_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "1");
    public static final FunctionLayerModule USER_REGISTER_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "2");
    public static final FunctionLayerModule USER_LOGIN_TYPE_NOT_VAILD_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "3");
    public static final FunctionLayerModule USER_VERIFICATION_CODE_NOT_VALID_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "4");
    public static final FunctionLayerModule USER_LOGIN_INFO_NOT_ENOUGH_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "5");
    public static final FunctionLayerModule USER_PROFILE_PHOTO_UPLOAD_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "6");
    public static final FunctionLayerModule USER_MESSAGE_CODE_NOT_VALID_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "7");
    public static final FunctionLayerModule USER_SEND_MESSAGE_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "8");
    public static final FunctionLayerModule USER_VERIFICATION_CODE_GENERATE_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "9");
    public static final FunctionLayerModule USER_PASSWORD_INCORRECT_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "10");
    public static final FunctionLayerModule USER_AUTHENTICATION_UNKOWN_LOGIN_TYPE_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "11");
    public static final FunctionLayerModule USER_AUTHENTICATION_UNKOWN_REGISTER_TYPE_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "12");
    public static final FunctionLayerModule USER_CLIENT_ID_NOT_REGISTER_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "13");
    public static final FunctionLayerModule USER_ONLY_LOGIN_BY_IMPLICT_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "14");
    public static final FunctionLayerModule USER_JUMP_URI_CONFIG_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "15");
    public static final FunctionLayerModule USER_JUMP_URI_NOT_REGISTER_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "16");
    public static final FunctionLayerModule USER_AUTHORIZE_RANGE_CONFIG_ERROR = new FunctionLayerModule(ACCOUNT_USER_MODULE_LAYER_MODULE, ACCOUNT_USER_MODEUL_NAME, "17");

}


