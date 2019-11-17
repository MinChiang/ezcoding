package com.ezcoding.sdk.account.user.constant;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-22 11:35
 */
public class AccountUserConstants {

    public static final int USER_ACCOUNT_LENGTH_MIN = 4;
    public static final int USER_ACCOUNT_LENGTH_MAX = 32;
    public static final int USER_NAME_LENGTH_MAX = 32;
    public static final int USER_ADDRESS_LENGTH_MAX = 128;
    public static final int USER_DESCRIPTION_LENGTH_MAX = 256;

    public static final String USER_PHONE_PATTERN = "^[1][3-9][0-9]{9}$";
    public static final String USER_EMAIL_PATTERN = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$";

    public static final int USER_REGISTER_VERIFICATION_CODE_LENGTH = 6;

}
