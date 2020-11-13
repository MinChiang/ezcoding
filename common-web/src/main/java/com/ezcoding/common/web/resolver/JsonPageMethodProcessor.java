package com.ezcoding.common.web.resolver;

import com.ezcoding.common.foundation.core.message.PageInfo;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-03 14:45
 */
public class JsonPageMethodProcessor extends AbstractMessageConverterMethodArgumentResolver {

    public JsonPageMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonPage.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        RequestMessage<JsonNode> requestMessage = requestMessageResolver.parse(
//                webRequest.getNativeRequest(HttpServletRequest.class)
//        );

        RequestMessage<JsonNode> requestMessage = null;

        //如果获取不到对象，直接返回默认对象
        if (requestMessage == null) {
            return null;
        }

        JsonPage parameterAnnotation = parameter.getParameterAnnotation(JsonPage.class);

        Class<?> parameterType = parameter.getParameterType();
        if (parameterType.isAssignableFrom(PageInfo.class)) {
            PageInfo result = requestMessage.getAppHead().getPageInfo();
            return fillDefaultValue(result, parameterAnnotation.defaultCurrentPage(), parameterAnnotation.defaultPageSize());
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

}
