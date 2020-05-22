package com.ezcoding.common.security.converter;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-07 15:24
 */
public class StandardAccessTokenConverter extends DefaultAccessTokenConverter {

    /**
     * 签发者
     */
    final String ISS = "iss";

    /**
     * 面向的用户
     */
    final String SUB = "sub";

    /**
     * 定义在什么时间之前，该jwt都是不可用的
     */
    final String NBF = "nbf";

    /**
     * 签发时间
     */
    final String IAT = "iat";

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> response = (Map<String, Object>) super.convertAccessToken(token, authentication);
        response.put(IAT, System.currentTimeMillis() / 1000);
        return response;
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        return super.extractAccessToken(value, map);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        return super.extractAuthentication(map);
    }

}
