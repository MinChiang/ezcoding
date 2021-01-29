package com.ezcoding.common.sdk.core;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-29 17:42
 */
public interface UrlConstants {

    char PATH_SPLIT = '/';
    String OAUTH_PUBLIC_KEY = "/oauth/public_key";

    static String formatUrl(String baseUrl, String relativePath) {
        return baseUrl + relativePath;
    }

}
