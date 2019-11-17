package com.ezcoding.account.module.user.exception;

import com.ezcoding.common.foundation.core.exception.ExceptionBuilderFactory;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-14 15:57
 */
public class UserExceptionConstants {

    public static final ExceptionBuilderFactory<UserException>
            USER_EXIST_ERROR = ExceptionBuilderFactory.create(UserException.class, "0001", "用户已经存在"),
            USER_NOT_EXIST_ERROR = ExceptionBuilderFactory.create(UserException.class, "0002", "用户不存在"),
            USER_REGISTER_ERROR = ExceptionBuilderFactory.create(UserException.class, "0003", "用户注册失败"),
            USER_LOGIN_TYPE_NOT_VAILD_ERROR = ExceptionBuilderFactory.create(UserException.class, "0004", "无效的登陆类型"),
            USER_VERIFICATION_CODE_NOT_VALID_ERROR = ExceptionBuilderFactory.create(UserException.class, "0005", "验证码错误"),
            USER_LOGIN_INFO_NOT_ENOUGH_ERROR = ExceptionBuilderFactory.create(UserException.class, "0006", "登陆信息不充分，请完备登陆信息"),
            USER_PROFILE_PHOTO_UPLOAD_ERROR = ExceptionBuilderFactory.create(UserException.class, "0007", "用户头像上传失败"),
            USER_MESSAGE_CODE_NOT_VALID_ERROR = ExceptionBuilderFactory.create(UserException.class, "0008", "验证码错误"),
            USER_SEND_MESSAGE_ERROR = ExceptionBuilderFactory.create(UserException.class, "0009", "验证码发送失败，请填写正确的手机号"),
            USER_VERIFICATION_CODE_GENERATE_ERROR = ExceptionBuilderFactory.create(UserException.class, "0010", "验证码生成失败"),
            USER_PASSWORD_INCORRECT_ERROR = ExceptionBuilderFactory.create(UserException.class, "0011", "密码不正确，请校验密码正确性"),
            USER_AUTHENTICATION_UNKOWN_LOGIN_TYPE_ERROR = ExceptionBuilderFactory.create(UserException.class, "0012", "未知的登陆类型"),
            USER_AUTHENTICATION_UNKOWN_REGISTER_TYPE_ERROR = ExceptionBuilderFactory.create(UserException.class, "0013", "未知的注册类型"),
            OAUTH_VALID_ERROR = ExceptionBuilderFactory.create(UserException.class, "0014", "认证错误：{0}");

}
