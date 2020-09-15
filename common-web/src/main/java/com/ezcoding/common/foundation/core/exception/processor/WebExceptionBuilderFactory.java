package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.BaseModuleExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratable;
import com.ezcoding.common.foundation.core.exception.WebExceptionCodeGenerator;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.util.Locale;
import java.util.Optional;

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
        return webExceptionBuilder(webExceptionCodeGenerator, LocaleContextHolder.getLocale());

    }

    /**
     * 生成可替换参数的错误构造器
     *
     * @param webExceptionCodeGenerator 生成器
     * @return 可替换参数的构造器
     */
    public static WebExceptionBuilder webExceptionBuilder(WebExceptionCodeGenerator webExceptionCodeGenerator, Locale locale) {
        return webExceptionBuilder(webExceptionCodeGenerator, webExceptionCodeGenerator.getTemplate(), webExceptionCodeGenerator.getHttpStatus(), Optional.ofNullable(locale).orElseGet(LocaleContextHolder::getLocale));
    }

    /**
     * 生成可替换参数的错误构造器
     *
     * @param generator  生成器
     * @param template   模板
     * @param httpStatus 响应状态码
     * @return 可替换参数的构造器
     */
    public static WebExceptionBuilder webExceptionBuilder(ExceptionCodeGeneratable generator, String template, HttpStatus httpStatus, Locale locale) {
        WebExceptionBuilder webExceptionBuilder = new WebExceptionBuilder(checkAndGenerateIdentification(generator), template, messageSource, locale);
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
