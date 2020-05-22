package com.ezcoding.account.extend.spring.security.filter;

import com.ezcoding.account.module.user.core.authentication.IAuthenticationService;
import com.google.common.collect.Maps;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-29 10:50
 */
public class MultipleOriginalFilter extends GenericFilterBean {

    private static final String LOGIN_TYPE_KEY = "login_type";
    private static final String ACCOUNT_KEY = "account";
    private static final String PASSWORD_KEY = "password";
    private static final String PHONE_KEY = "phone";
    private static final String RECEIPT_KEY = "receipt";
    private static final String VERIFICATION_CODE_KEY = "verification_code";

    private String accountName = ACCOUNT_KEY;
    private String passwordName = PASSWORD_KEY;
    private String phoneName = PHONE_KEY;
    private String receiptName = RECEIPT_KEY;
    private String verificationCodeName = VERIFICATION_CODE_KEY;
    private String loginTypeName = LOGIN_TYPE_KEY;

    private IAuthenticationService authenticationService;
    private RequestMatcher requiresAuthenticationRequestMatcher;

    public MultipleOriginalFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        this.requiresAuthenticationRequestMatcher = requiresAuthenticationRequestMatcher;
    }

    public MultipleOriginalFilter(String defaultFilterProcessesUrl) {
        this.requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(defaultFilterProcessesUrl);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }

        String loginType = req.getParameter(loginTypeName);
        String account = req.getParameter(accountName);
        String password = req.getParameter(passwordName);
        String phone = req.getParameter(phoneName);
        String receipt = req.getParameter(receiptName);
        String verificationCode = req.getParameter(verificationCodeName);

        //请求规则映射转换
        Map<String, Object> context = Maps.newHashMap();
        context.put(IAuthenticationService.LOGIN_TYPE_KEY, loginType);
        context.put(IAuthenticationService.ACCOUNT_KEY, account);
        context.put(IAuthenticationService.PASSWORD_KEY, password);
        context.put(IAuthenticationService.PHONE_KEY, phone);
        context.put(IAuthenticationService.RECEIPT_KEY, receipt);
        context.put(IAuthenticationService.VERIFICATION_CODE_KEY, verificationCode);

        authenticationService.login(context);
        chain.doFilter(request, response);
    }

    /**
     * 判断是否需要进行用户身份验证
     *
     * @param request  请求
     * @param response 响应
     * @return 是否需要用户身份验证
     */
    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPasswordName() {
        return passwordName;
    }

    public void setPasswordName(String passwordName) {
        this.passwordName = passwordName;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public String getVerificationCodeName() {
        return verificationCodeName;
    }

    public void setVerificationCodeName(String verificationCodeName) {
        this.verificationCodeName = verificationCodeName;
    }

    public String getLoginTypeName() {
        return loginTypeName;
    }

    public void setLoginTypeName(String loginTypeName) {
        this.loginTypeName = loginTypeName;
    }

    public IAuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void setAuthenticationService(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public RequestMatcher getRequiresAuthenticationRequestMatcher() {
        return requiresAuthenticationRequestMatcher;
    }

    public void setRequiresAuthenticationRequestMatcher(RequestMatcher requiresAuthenticationRequestMatcher) {
        this.requiresAuthenticationRequestMatcher = requiresAuthenticationRequestMatcher;
    }

}
