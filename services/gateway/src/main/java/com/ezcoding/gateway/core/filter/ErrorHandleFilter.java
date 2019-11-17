package com.ezcoding.gateway.core.filter;

import com.ezcoding.common.foundation.core.exception.AbstractApplicationException;
import com.ezcoding.gateway.core.ApplicationFilterConstants;
import com.ezcoding.gateway.util.ZuulResponseUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-21 11:36
 */
@Deprecated
public class ErrorHandleFilter extends ZuulFilter {

    @Autowired
    private SendResponseFilter sendResponseFilter;

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return ApplicationFilterConstants.ERROR_ERROR_HANDLE_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().getThrowable() != null;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        Throwable cause = currentContext.getThrowable().getCause();
        if (cause instanceof AbstractApplicationException) {
            ZuulResponseUtils.responseError((AbstractApplicationException) cause, HttpStatus.INTERNAL_SERVER_ERROR);
            //不执行后续的SendErrorFilter
            currentContext.put("sendErrorFilter.ran", true);
            //执行最终的写出流操作
            sendResponseFilter.run();
        }
        return null;
    }

}
