package com.ezcoding.module.user.exception;

import com.ezcoding.common.foundation.core.exception.WebExceptionCodeGenerator;

import static com.ezcoding.module.user.exception.AccountUserModuleConstants.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-14 15:57
 */
public class AccountUserExceptionConstants {

    public static final WebExceptionCodeGenerator GEN_USER_EXIST_ERROR = new WebExceptionCodeGenerator(USER_EXIST_ERROR, "USER_EXIST_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_NOT_EXIST_ERROR = new WebExceptionCodeGenerator(USER_NOT_EXIST_ERROR, "USER_NOT_EXIST_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_REGISTER_ERROR = new WebExceptionCodeGenerator(USER_REGISTER_ERROR, "USER_REGISTER_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_LOGIN_TYPE_NOT_VAILD_ERROR = new WebExceptionCodeGenerator(USER_LOGIN_TYPE_NOT_VAILD_ERROR, "USER_LOGIN_TYPE_NOT_VAILD_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_VERIFICATION_CODE_NOT_VALID_ERROR = new WebExceptionCodeGenerator(USER_VERIFICATION_CODE_NOT_VALID_ERROR, "USER_VERIFICATION_CODE_NOT_VALID_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_LOGIN_INFO_NOT_ENOUGH_ERROR = new WebExceptionCodeGenerator(USER_LOGIN_INFO_NOT_ENOUGH_ERROR, "USER_LOGIN_INFO_NOT_ENOUGH_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_PROFILE_PHOTO_UPLOAD_ERROR = new WebExceptionCodeGenerator(USER_PROFILE_PHOTO_UPLOAD_ERROR, "USER_PROFILE_PHOTO_UPLOAD_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_MESSAGE_CODE_NOT_VALID_ERROR = new WebExceptionCodeGenerator(USER_MESSAGE_CODE_NOT_VALID_ERROR, "USER_MESSAGE_CODE_NOT_VALID_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_SEND_MESSAGE_ERROR = new WebExceptionCodeGenerator(USER_SEND_MESSAGE_ERROR, "USER_SEND_MESSAGE_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_VERIFICATION_CODE_GENERATE_ERROR = new WebExceptionCodeGenerator(USER_VERIFICATION_CODE_GENERATE_ERROR, "USER_VERIFICATION_CODE_GENERATE_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_PASSWORD_INCORRECT_ERROR = new WebExceptionCodeGenerator(USER_PASSWORD_INCORRECT_ERROR, "USER_PASSWORD_INCORRECT_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_AUTHENTICATION_UNKOWN_LOGIN_TYPE_ERROR = new WebExceptionCodeGenerator(USER_AUTHENTICATION_UNKOWN_LOGIN_TYPE_ERROR, "USER_AUTHENTICATION_UNKOWN_LOGIN_TYPE_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_AUTHENTICATION_UNKOWN_REGISTER_TYPE_ERROR = new WebExceptionCodeGenerator(USER_AUTHENTICATION_UNKOWN_REGISTER_TYPE_ERROR, "USER_AUTHENTICATION_UNKOWN_REGISTER_TYPE_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_CLIENT_ID_NOT_REGISTER_ERROR = new WebExceptionCodeGenerator(USER_CLIENT_ID_NOT_REGISTER_ERROR, "USER_CLIENT_ID_NOT_REGISTER_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_ONLY_LOGIN_BY_IMPLICT_ERROR = new WebExceptionCodeGenerator(USER_ONLY_LOGIN_BY_IMPLICT_ERROR, "USER_ONLY_LOGIN_BY_IMPLICT_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_JUMP_URI_CONFIG_ERROR = new WebExceptionCodeGenerator(USER_JUMP_URI_CONFIG_ERROR, "USER_JUMP_URI_CONFIG_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_JUMP_URI_NOT_REGISTER_ERROR = new WebExceptionCodeGenerator(USER_JUMP_URI_NOT_REGISTER_ERROR, "USER_JUMP_URI_NOT_REGISTER_ERROR");
    public static final WebExceptionCodeGenerator GEN_USER_AUTHORIZE_RANGE_CONFIG_ERROR = new WebExceptionCodeGenerator(USER_AUTHORIZE_RANGE_CONFIG_ERROR, "USER_AUTHORIZE_RANGE_CONFIG_ERROR");

}
