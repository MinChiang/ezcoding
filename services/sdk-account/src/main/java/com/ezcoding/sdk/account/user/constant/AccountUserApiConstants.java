package com.ezcoding.sdk.account.user.constant;

import com.ezcoding.sdk.account.user.bean.model.UserFieldDetailEnum;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-09 15:35
 */
public class AccountUserApiConstants {

    public static final String USER_API = "user";
    public static final String PERMISSION_API = "permission";

    public static final String VERIFICATION_CODE = "verificationCode";
    public static final String MESSAGE_CODE = "messageCode";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String AUTHORIZE = "authorize";
    public static final String CHANGE_PROFILE_PHOTO = "changeProfilePhoto";
    public static final String GET_USER_INFO = "getUserInfo";
    public static final String LIST_USERS_INFO = "listUsersInfo";
    public static final String USER_IS_EXIST = "exist";
    public static final String MODIFY_INFO = "modifyInfo";
    public static final String KICK_OUT = "kickOut";

    public static final String LOAD_PERMISSION = "loadPermission";

    public static final Set<UserFieldDetailEnum> USER_FIELD_DETAILS = ImmutableSet
            .<UserFieldDetailEnum>builder()
            .add(UserFieldDetailEnum.ID)
            .add(UserFieldDetailEnum.CODE)
            .add(UserFieldDetailEnum.ACCOUNT)
            .add(UserFieldDetailEnum.NAME)
            .add(UserFieldDetailEnum.GENDER)
            .add(UserFieldDetailEnum.PHONE)
            .add(UserFieldDetailEnum.EMAIL)
            .add(UserFieldDetailEnum.ADDRESS)
            .add(UserFieldDetailEnum.BIRTHDAY)
            .add(UserFieldDetailEnum.HIRE_DATE)
            .add(UserFieldDetailEnum.PROFILE_PHOTO)
            .add(UserFieldDetailEnum.DESCRIPTION)
            .add(UserFieldDetailEnum.VERIFIED)
            .add(UserFieldDetailEnum.AREA)
            .build();

}
