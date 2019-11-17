package com.ezcoding.account.module.user.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-08 16:39
 */
public interface IAuthorizationService {

    /**
     * 构建第三方认证的请求
     *
     * @param context 认证请求参数
     * @return 认证请求返回内容
     */
    OAuth2AccessToken authorize(Map<String, ?> context);

}
