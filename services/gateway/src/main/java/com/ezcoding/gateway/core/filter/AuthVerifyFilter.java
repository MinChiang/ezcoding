package com.ezcoding.gateway.core.filter;

import com.ezcoding.common.foundation.core.constant.GlobalConstants;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.RequestMessageBuilder;
import com.ezcoding.gateway.core.ApplicationFilterConstants;
import com.ezcoding.gateway.util.ZuulResponseUtils;
import com.ezcoding.sdk.auth.resource.api.AuthFeignClient;
import com.ezcoding.sdk.auth.resource.bean.model.ResourceTypeEnum;
import com.google.common.collect.ImmutableMap;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-20 17:29
 */
public class AuthVerifyFilter extends ZuulFilter {

    private AuthFeignClient authFeignClient;

    public AuthVerifyFilter(AuthFeignClient authFeignClient) {
        this.authFeignClient = authFeignClient;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return ApplicationFilterConstants.PRE_AUTH_VERIFY_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        String requestURI = currentContext.getRequest().getRequestURI();
        ResponseMessage<Boolean> booleanResponseMessage = authFeignClient.hasResource(
                RequestMessageBuilder
                        .create(
                                ImmutableMap
                                        .builder()
                                        .put("type", ResourceTypeEnum.ACCESS_PATH)
                                        .put("applicationName", GlobalConstants.Application.GATEWAY)
                                        .put("accessPath", requestURI)
                                        .build()
                        ).build()
        );

        if (!booleanResponseMessage.getPayload()) {
            ZuulResponseUtils.responseError(CommonApplicationExceptionConstants.COMMON_NO_PERMISSION_ERROR, HttpStatus.FORBIDDEN);
        }
        return null;
    }

}
