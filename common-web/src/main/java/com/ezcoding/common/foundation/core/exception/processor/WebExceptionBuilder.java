package com.ezcoding.common.foundation.core.exception.processor;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-02-15 15:26
 */
public class WebExceptionBuilder extends MessageSourceTemplateExceptionBuilder {

    /**
     * 响应状态
     */
    private HttpStatus httpStatus;

    public WebExceptionBuilder(String identification, MessageSource messageSource) {
        super(identification, messageSource);
    }

    public WebExceptionBuilder(String identification, MessageSource messageSource, HttpStatus httpStatus) {
        super(identification, messageSource);
        this.httpStatus = httpStatus;
    }

    /**
     * 设定响应状态码
     *
     * @param httpStatus 设定的状态码
     * @return 实例对象
     */
    public WebExceptionBuilder httpStatus(HttpStatus httpStatus) {
        return this;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
