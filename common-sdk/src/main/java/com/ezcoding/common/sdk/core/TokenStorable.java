package com.ezcoding.common.sdk.core;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-30 9:08
 */
public interface TokenStorable {

    /**
     * 保存token
     *
     * @param token 待保存的token
     */
    void setToken(Token token);

    /**
     * 获取token
     *
     * @return 已保存的token
     */
    Token getToken();

}
