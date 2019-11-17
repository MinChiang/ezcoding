package com.ezcoding.web.resolver;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.base.web.util.PageUtils;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import com.ezcoding.common.foundation.core.message.head.PageInfo;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-03 14:45
 */
public class JsonPageMethodProcessor implements HandlerMethodArgumentResolver {

    private IMessageBuilder messageBuilder;
    private JsonRequestMessageResolver requestMessageResolver;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonPage.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestMessage<JsonNode> requestMessage = requestMessageResolver.resolve(
                webRequest.getNativeRequest(HttpServletRequest.class)
        );

        //如果获取不到对象，直接返回默认对象
        if (requestMessage == null) {
            return null;
        }

        JsonPage parameterAnnotation = parameter.getParameterAnnotation(JsonPage.class);

        Class<?> parameterType = parameter.getParameterType();
        if (parameterType.isAssignableFrom(PageInfo.class)) {
            PageInfo result = requestMessage.getAppHead().getPageInfo();
            return fillDefaultValue(result, parameterAnnotation.defaultCurrentPage(), parameterAnnotation.defaultPageSize());
        } else if (parameterType.isAssignableFrom(Page.class)) {
            PageInfo pageInfo = requestMessage.getAppHead().getPageInfo();
            pageInfo = fillDefaultValue(pageInfo, parameterAnnotation.defaultCurrentPage(), parameterAnnotation.defaultPageSize());
            Page<Object> page = PageUtils.convertToPage(pageInfo);
            page.setSearchCount(parameterAnnotation.searchCount());
            return page;
        }

        return null;
    }

    /**
     * 填充默认值
     *
     * @param pageInfo           待填充的分页信息
     * @param defaultCurrentPage 默认的当前页
     * @param defaultPageSize    默认的分页大小
     * @return 填充后的分页
     */
    private PageInfo fillDefaultValue(PageInfo pageInfo, int defaultCurrentPage, int defaultPageSize) {
        if (pageInfo == null) {
            return new PageInfo(defaultCurrentPage, defaultPageSize);
        }
        if (pageInfo.getCurrentPage() == null) {
            pageInfo.setCurrentPage(defaultCurrentPage);
        }
        if (pageInfo.getPageSize() == null) {
            pageInfo.setPageSize(defaultPageSize);
        }
        return pageInfo;
    }

    public IMessageBuilder getMessageBuilder() {
        return messageBuilder;
    }

    public void setMessageBuilder(IMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    public JsonRequestMessageResolver getRequestMessageResolver() {
        return requestMessageResolver;
    }

    public void setRequestMessageResolver(JsonRequestMessageResolver requestMessageResolver) {
        this.requestMessageResolver = requestMessageResolver;
    }
}
