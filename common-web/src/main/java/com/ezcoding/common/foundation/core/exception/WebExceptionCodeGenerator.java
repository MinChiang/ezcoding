package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import org.springframework.http.HttpStatus;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-02-15 15:36
 */
public class WebExceptionCodeGenerator extends TemplateExceptionCodeGenerator {

    /**
     * 响应状态
     */
    private HttpStatus httpStatus;

    public WebExceptionCodeGenerator(ModuleLayerModule moduleLayerModule, String errorSuffixCode, String template, HttpStatus httpStatus) {
        super(moduleLayerModule, errorSuffixCode, template);
        this.httpStatus = httpStatus;
    }

    public WebExceptionCodeGenerator(ModuleLayerModule moduleLayerModule, String errorSuffixCode, String template) {
        this(moduleLayerModule, errorSuffixCode, template, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public WebExceptionCodeGenerator(ModuleLayerModule moduleLayerModule, int errorSuffixCode, String template, HttpStatus httpStatus) {
        this(moduleLayerModule, String.valueOf(errorSuffixCode), template, httpStatus);
    }

    public WebExceptionCodeGenerator(ModuleLayerModule moduleLayerModule, int errorSuffixCode, String template) {
        this(moduleLayerModule, errorSuffixCode, template, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
