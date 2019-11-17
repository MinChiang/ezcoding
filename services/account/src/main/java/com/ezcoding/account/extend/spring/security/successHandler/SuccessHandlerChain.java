package com.ezcoding.account.extend.spring.security.successHandler;

import com.google.common.collect.Lists;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-20 0:02
 */
public class SuccessHandlerChain implements AuthenticationSuccessHandler {

    private List<AuthenticationSuccessHandler> chain = Lists.newArrayList();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        for (AuthenticationSuccessHandler authenticationSuccessHandler : chain) {
            authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        }
    }

    public void addAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.chain.add(authenticationSuccessHandler);
    }

}
