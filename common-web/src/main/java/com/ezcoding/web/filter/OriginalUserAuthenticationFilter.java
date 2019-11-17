package com.ezcoding.web.filter;

import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 原生的用户鉴定器
 * 反序列化明文的用户凭证
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-24 15:36
 */
@Deprecated
public class OriginalUserAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "CUSTOM_AUTHORIZATION";

    private ObjectMapper objectMapper;
    private AuthSettings authSettings;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader(authSettings.getHeader());
        if (StringUtils.isNotBlank(token)) {
            RequestContextHolder.getRequestAttributes().setAttribute(AUTHORIZATION, token, RequestAttributes.SCOPE_REQUEST);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public AuthSettings getAuthSettings() {
        return authSettings;
    }

    public void setAuthSettings(AuthSettings authSettings) {
        this.authSettings = authSettings;
    }
}
