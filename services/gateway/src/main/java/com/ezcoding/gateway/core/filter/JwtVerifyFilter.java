package com.ezcoding.gateway.core.filter;

import com.ezcoding.common.foundation.core.constant.GlobalConstants;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.core.tools.jwt.TokenTools;
import com.ezcoding.gateway.core.ApplicationFilterConstants;
import com.ezcoding.gateway.util.ZuulResponseUtils;
import com.ezcoding.sdk.account.user.core.IKickOutVerifiable;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * 详见
 * org.springframework.cloud.netflix.zuul.filters.support.FilterConstants
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-20 9:19
 */
public class JwtVerifyFilter extends ZuulFilter {

    private static final String DEFAULT_TOKEN_HEADER = GlobalConstants.Header.AUTHORIZATION;
    private static final String TOKEN_HEADER_PREFIX = "Bearer ";
    private static final String PRINCIPAL_KEY = "principal";

    private String tokenHeader;
    private TokenTools tokenTools;
    private IKickOutVerifiable kickOutVerifiable;

    public JwtVerifyFilter(String tokenHeader, TokenTools tokenTools, IKickOutVerifiable kickOutVerifiable) {
        if (StringUtils.isEmpty(tokenHeader)) {
            this.tokenHeader = DEFAULT_TOKEN_HEADER;
        } else {
            this.tokenHeader = tokenHeader;
        }
        this.tokenTools = tokenTools;
        this.kickOutVerifiable = kickOutVerifiable;
    }

    public JwtVerifyFilter(String tokenHeader, TokenTools tokenTools) {
        this(tokenHeader, tokenTools, null);
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return ApplicationFilterConstants.PRE_JWT_VERIFY_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        String header = RequestContext.getCurrentContext().getRequest().getHeader(tokenHeader);
        return header != null && header.startsWith(TOKEN_HEADER_PREFIX);
    }

    /**
     * 获取当前的用户
     *
     * @return 请求用户
     */
    private String validateKickOutTimeAndGetUser() {
        String header = RequestContext.getCurrentContext().getRequest().getHeader(tokenHeader);
        String token = StringUtils.substringAfter(header, TOKEN_HEADER_PREFIX);
        String userCode = null;
        try {
            Claims claims = tokenTools.parseToken(token);
            userCode = claims.get(PRINCIPAL_KEY, String.class);
            Date issuedAt = claims.getIssuedAt();
            if (kickOutVerifiable != null && kickOutVerifiable.isKickedOut(userCode, issuedAt)) {
                ZuulResponseUtils.responseError(CommonApplicationExceptionConstants.COMMON_USER_IS_KICKED_OUT.instance().build(), HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            ZuulResponseUtils.responseError(CommonApplicationExceptionConstants.COMMON_TOKEN_PARSE_ERROR.instance().cause(e).build(), HttpStatus.FORBIDDEN);
        }
        return userCode;
    }

    @Override
    public Object run() {
        String userCode = this.validateKickOutTimeAndGetUser();
        if (userCode != null) {
            RequestContext.getCurrentContext().put(ApplicationFilterConstants.USER_CODE_KEY, userCode);
        }
        return null;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

}
