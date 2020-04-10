package com.ezcoding.extend.spring.security.failureHandler;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.head.ErrorAppHead;
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
import java.net.URLEncoder;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-11 15:37
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final String DEFAULT_ERROR_CODE_PARAM = "error_code";
    private static final String DEFAULT_ERROR_MESSAGE_PARAM = "error_message";

    protected String defaultFailureUrl;
    protected String errorCodeParam = DEFAULT_ERROR_CODE_PARAM;
    protected String errorMessageParam = DEFAULT_ERROR_MESSAGE_PARAM;
    protected boolean forwardToDestination = false;
    protected RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomAuthenticationFailureHandler(String defaultFailureUrl) {
        this(defaultFailureUrl, null, null);
    }

    public CustomAuthenticationFailureHandler(String defaultFailureUrl, String errorCodeParam, String errorMessageParam) {
        this.defaultFailureUrl = defaultFailureUrl;
        if (StringUtils.isNotEmpty(errorCodeParam)) {
            this.errorCodeParam = errorCodeParam;
        }
        if (StringUtils.isNotEmpty(errorMessageParam)) {
            this.errorMessageParam = errorMessageParam;
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (defaultFailureUrl == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        } else {
            String url;
            String code = ErrorAppHead.getDefaultErrorCode();
            String message = ErrorAppHead.getDefaultErrorMessage();
            if (exception.getCause() instanceof ApplicationException) {
                code = ((ApplicationException) exception.getCause()).getIdentification();
                message = exception.getCause().getLocalizedMessage();
            }

            url = defaultFailureUrl.contains("?") ? defaultFailureUrl + "&" : defaultFailureUrl + "?";
            url = url + errorCodeParam + "=" + URLEncoder.encode(code) + "&";
            url = url + errorMessageParam + "=" + URLEncoder.encode(message);

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
