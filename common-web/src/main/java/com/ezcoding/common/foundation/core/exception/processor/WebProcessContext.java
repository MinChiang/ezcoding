package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.processor.ProcessContext;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-19 16:26
 */
public class WebProcessContext extends ProcessContext {

    /**
     * http请求
     */
    private HttpServletRequest request;

    /**
     * http响应
     */
    private HttpServletResponse response;

    /**
     * 处理器
     */
    private Object handler;

    /**
     * 返回错误信息
     */
    private String returnSummary;

    /**
     * 返回错误码
     */
    private HttpStatus httpStatus;

    public WebProcessContext(HttpServletRequest request, HttpServletResponse response, Object handler) {
        this.request = request;
        this.response = response;
        this.handler = handler;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public String getReturnSummary() {
        return returnSummary;
    }

    public void setReturnSummary(String returnSummary) {
        this.returnSummary = returnSummary;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
