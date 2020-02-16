package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.IExceptionCodeGeneratable;
import com.ezcoding.common.foundation.core.exception.WebExceptionCodeGenerator;
import org.springframework.http.HttpStatus;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-02-15 15:19
 */
public class WebExceptionBuilderFactory extends ModuleExceptionBuilderFactory {

    /**
     * 生成可替换参数的错误构造器
     *
     * @param webExceptionCodeGenerator 生成器
     * @return 可替换参数的构造器
     */
    public static WebExceptionBuilder webExceptionBuilder(WebExceptionCodeGenerator webExceptionCodeGenerator) {
        return new WebExceptionBuilder(checkAndGenerate(webExceptionCodeGenerator), getMessageSource(), webExceptionCodeGenerator.getHttpStatus());
    }

    /**
     * 生成可替换参数的错误构造器
     *
     * @param generator  生成器
     * @param template   模板
     * @param params     替换参数
     * @param httpStatus 响应状态码
     * @return 可替换参数的构造器
     */
    public static WebExceptionBuilder webExceptionBuilder(IExceptionCodeGeneratable generator, String template, Object[] params, HttpStatus httpStatus) {
        WebExceptionBuilder webExceptionBuilder = new WebExceptionBuilder(checkAndGenerate(generator), getMessageSource(), httpStatus);
        webExceptionBuilder.setTemplate(template);
        webExceptionBuilder.addParams(params);
        webExceptionBuilder.setHttpStatus(httpStatus);
        return webExceptionBuilder;
    }

}
