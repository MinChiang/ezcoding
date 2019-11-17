package com.ezcoding.account.extend.spring.security.failureHandler;

import com.ezcoding.common.foundation.core.exception.AbstractApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-11 15:37
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final String DEFAULT_ERROR_CODE_PARAM = "error_code";

    protected String defaultFailureUrl;
    protected String errorCodeParam = DEFAULT_ERROR_CODE_PARAM;
    protected boolean forwardToDestination = false;
    protected RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomAuthenticationFailureHandler(String defaultFailureUrl) {
        this(defaultFailureUrl, null);
    }

    public CustomAuthenticationFailureHandler(String defaultFailureUrl, String errorCodeParam) {
        this.defaultFailureUrl = defaultFailureUrl;
        if (StringUtils.isNotEmpty(errorCodeParam)) {
            this.errorCodeParam = errorCodeParam;
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (defaultFailureUrl == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        } else {
            String url = this.defaultFailureUrl;
            if (exception.getCause() instanceof AbstractApplicationException) {
                String code = ((AbstractApplicationException) exception.getCause()).getCode();
                url = (defaultFailureUrl.contains("?") ? defaultFailureUrl + "&" : defaultFailureUrl + "?") + errorCodeParam + "=" + code;
            }
            if (forwardToDestination) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                redirectStrategy.sendRedirect(request, response, url);
            }
        }
    }

    public String getDefaultFailureUrl() {
        return defaultFailureUrl;
    }

    public boolean isForwardToDestination() {
        return forwardToDestination;
    }

    public void setForwardToDestination(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    public String getErrorCodeParam() {
        return errorCodeParam;
    }

}
