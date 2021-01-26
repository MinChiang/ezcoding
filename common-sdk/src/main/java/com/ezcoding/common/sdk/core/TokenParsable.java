package com.ezcoding.common.sdk.core;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-26 10:51
 */
public interface TokenParsable {

    /**
     * 令牌是否过期
     *
     * @return 是否过期
     */
    boolean isTokenExpired();

    /**
     * 解析令牌
     *
     * @return 解析内容
     */
    UserAuthentication acquireUserAuthentication();

}
