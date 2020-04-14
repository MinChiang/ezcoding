package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.BaseModuleExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.exception.IExceptionCodeGeneratable;
import com.ezcoding.common.foundation.core.exception.WebExceptionCodeGenerator;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-02-15 15:19
 */
public class WebExceptionBuilderFactory extends BaseModuleExceptionBuilderFactory {

    /**
     * 资源管理器
     */
    private static MessageSource messageSource;

    /**
     * 生成可替换参数的错误构造器
     *
     * @param webExceptionCodeGenerator 生成器
     * @return 可替换参数的构造器
     */
    public static WebExceptionBuilder webExceptionBuilder(WebExceptionCodeGenerator webExceptionCodeGenerator) {
        WebExceptionBuilder webExceptionBuilder = new WebExceptionBuilder(checkAndGenerateIdentification(webExceptionCodeGenerator), webExceptionCodeGenerator.getTemplate(), messageSource);
        webExceptionBuilder.setHttpStatus(webExceptionCodeGenerator.getHttpStatus());
        return webExceptionBuilder;
    }

    /**
     * 生成可替换参数的错误构造器
     *
     * @param generator  生成器
     * @param template   模板
     * @param httpStatus 响应状态码
     * @return 可替换参数的构造器
     */
    public static WebExceptionBuilder webExceptionBuilder(IExceptionCodeGeneratable generator, String template, HttpStatus httpStatus) {
        WebExceptionBuilder webExceptionBuilder = new WebExceptionBuilder(checkAndGenerateIdentification(generator), template, messageSource);
        webExceptionBuilder.setHttpStatus(httpStatus);
        return webExceptionBuilder;
    }

    public static MessageSource getMessageSource() {
        return messageSource;
    }

    public static void setMessageSource(MessageSource messageSource) {
        WebExceptionBuilderFactory.messageSource = messageSource;
    }

}
