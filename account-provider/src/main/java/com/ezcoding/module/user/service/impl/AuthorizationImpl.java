package com.ezcoding.module.user.service.impl;

import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.module.user.service.IAuthorizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.endpoint.AbstractEndpoint;
import org.springframework.security.oauth2.provider.endpoint.DefaultRedirectResolver;
import org.springframework.security.oauth2.provider.endpoint.RedirectResolver;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_PARAM_VALIDATE_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-08 16:40
 */
@Deprecated
public class AuthorizationImpl extends AbstractEndpoint implements IAuthorizationService {

    private static final String CLIENT_ID = "clientId";
    private static final String STATE = "state";
    private static final String SCOPE = "scope";
    private static final String REDIRECT_URI = "redirectUri";
    private static final String RESPONSE_TYPE = "responseType";

    private static final String TOKEN = "token";
    private static final String IMPLICIT = "implicit";

    private final Object LOCK = new Object();
    private RedirectResolver redirectResolver = new DefaultRedirectResolver();
    private OAuth2RequestValidator oauth2RequestValidator = new DefaultOAuth2RequestValidator();

    @Override
    public OAuth2AccessToken authorize(Map<String, ?> context) {
        AuthorizationRequest authorizationRequest = getOAuth2RequestFactory().createAuthorizationRequest(convertRequestParam(context));
        Set<String> responseTypes = authorizationRequest.getResponseTypes();

        AssertUtils.mustTrue(responseTypes.contains(TOKEN), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_ONLY_LOGIN_BY_IMPLICT_ERROR).build());
        AssertUtils.mustNotNull(authorizationRequest.getClientId(), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());

        ClientDetails client = getClientDetailsService().loadClientByClientId(authorizationRequest.getClientId());
        String redirectUriParameter = authorizationRequest.getRequestParameters().get(OAuth2Utils.REDIRECT_URI);
        String resolvedRedirect = null;
        try {
            resolvedRedirect = redirectResolver.resolveRedirect(redirectUriParameter, client);
        } catch (OAuth2Exception e) {
            throw WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_JUMP_URI_CONFIG_ERROR).build();
        }
        AssertUtils.mustTrue(StringUtils.isNotBlank(resolvedRedirect), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_JUMP_URI_NOT_REGISTER_ERROR).build());
        authorizationRequest.setRedirectUri(resolvedRedirect);
        try {
            oauth2RequestValidator.validateScope(authorizationRequest, client);
        } catch (InvalidScopeException e) {
            throw WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_AUTHORIZE_RANGE_CONFIG_ERROR).build();
        }
        TokenRequest tokenRequest = getOAuth2RequestFactory().createTokenRequest(authorizationRequest, IMPLICIT);
        OAuth2Request storedOAuth2Request = getOAuth2RequestFactory().createOAuth2Request(authorizationRequest);
        synchronized (this.LOCK) {
            return getTokenGranter().grant(IMPLICIT, new ImplicitTokenRequest(tokenRequest, storedOAuth2Request));
        }
    }

    /**
     * 转换原生请求字段
     *
     * @param context 原生请求字段
     * @return 转换后的字段
     */
    private Map<String, String> convertRequestParam(Map<String, ?> context) {
        String responseType = context.get(RESPONSE_TYPE).toString();
        String clientId = context.get(CLIENT_ID).toString();
        AssertUtils.mustTrue(StringUtils.isNotBlank(clientId) && StringUtils.isNotBlank(responseType), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());

        Map<String, String> map = new HashMap<>();
        map.put(OAuth2Utils.RESPONSE_TYPE, responseType);
        map.put(OAuth2Utils.CLIENT_ID, clientId);

        map.put(OAuth2Utils.REDIRECT_URI, context.get(REDIRECT_URI).toString());
        map.put(OAuth2Utils.SCOPE, context.get(SCOPE).toString());
        map.put(OAuth2Utils.STATE, context.get(STATE).toString());
        return map;
    }

}
