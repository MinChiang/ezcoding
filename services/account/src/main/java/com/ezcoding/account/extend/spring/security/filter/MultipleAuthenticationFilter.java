package com.ezcoding.account.extend.spring.security.filter;

import com.ezcoding.account.module.user.core.authentication.IAuthenticationService;
import com.ezcoding.sdk.account.user.bean.model.DeviceTypeEnum;
import com.ezcoding.sdk.account.user.bean.model.LoginRegisterTypeEnum;
import com.ezcoding.sdk.account.user.util.UserUtils;
import com.google.common.collect.ImmutableMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-29 10:50
 */
public class MultipleAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

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

    public MultipleAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public MultipleAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        String account = req.getParameter(accountName);
        String password = req.getParameter(passwordName);
        String phone = req.getParameter(phoneName);
        String receipt = req.getParameter(receiptName);
        String verificationCode = req.getParameter(verificationCodeName);
        String originLoginType = req.getParameter(loginTypeName);

        //获取对应的登陆设备类型
        DeviceTypeEnum deviceType = UserUtils.distinguishDeviceType(req);

        //获取对应的登陆类型
        LoginRegisterTypeEnum loginType = UserUtils.distinguishLoginType(originLoginType);
        if (loginType == null || loginType == LoginRegisterTypeEnum.UNKNOWN) {
            loginType = LoginRegisterTypeEnum.ACCOUNT_PASSWORD;
        }

        //请求规则映射转换
        Map<String, Object> context =
                ImmutableMap
                        .<String, Object>builder()
                        .put(IAuthenticationService.LOGIN_TYPE_KEY, loginType)
                        .put(IAuthenticationService.DEVICE_TYPE, deviceType)
                        .put(IAuthenticationService.ACCOUNT_KEY, account)
                        .put(IAuthenticationService.PASSWORD_KEY, password)
                        .put(IAuthenticationService.PHONE_KEY, phone)
                        .put(IAuthenticationService.RECEIPT_KEY, receipt)
                        .put(IAuthenticationService.VERIFICATION_CODE_KEY, verificationCode)
                        .build();

        Authentication authentication;
        try {
            authentication = authenticationService.login(context);
        } catch (Exception e) {
            throw new MultipleAuthenticationException(e.getMessage(), e);
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    private static final class MultipleAuthenticationException extends AuthenticationException {

        public MultipleAuthenticationException(String msg, Throwable t) {
            super(msg, t);
        }

        public MultipleAuthenticationException(String msg) {
            super(msg);
        }

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
}
