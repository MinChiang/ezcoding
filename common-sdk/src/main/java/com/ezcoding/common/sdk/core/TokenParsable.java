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
     * @param token 令牌
     * @return 是否过期
     */
    boolean isTokenExpired(String token);

    /**
     * 解析令牌
     *
     * @param token 令牌
     * @return 解析内容
     */
    UserAuthentication parseToken(String token);

}
