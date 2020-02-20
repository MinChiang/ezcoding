package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
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

    public WebExceptionCodeGenerator(FunctionLayerModule functionLayerModule, String template, HttpStatus httpStatus) {
        super(functionLayerModule, template);
        this.httpStatus = httpStatus;
    }

    public WebExceptionCodeGenerator(FunctionLayerModule functionLayerModule, String template) {
        this(functionLayerModule, template, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
