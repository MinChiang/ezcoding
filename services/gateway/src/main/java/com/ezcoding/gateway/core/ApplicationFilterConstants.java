package com.ezcoding.gateway.core;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-20 9:28
 */
public class ApplicationFilterConstants {

    public static final int ERROR_ERROR_HANDLE_FILTER_ORDER = -1;

    public static final int PRE_JWT_VERIFY_FILTER_ORDER = 2;

    public static final int PRE_AUTH_VERIFY_FILTER_ORDER = 3;

    public static final String USER_CODE_KEY = "jwtVerifyFilter.userCode";

}
