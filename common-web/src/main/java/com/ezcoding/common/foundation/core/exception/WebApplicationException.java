package com.ezcoding.common.foundation.core.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-02-11 15:41
 */
public class WebApplicationException extends ApplicationException {

    /**
     * 返回状态码
     */
    protected HttpStatus returnStatus;

    /**
     * 返回信息
     */
    protected String returnMessage;

    public WebApplicationException(String identification, String printMessage, String returnMessage, HttpStatus returnStatus, Throwable cause) {
        super(identification, printMessage, cause, new HashMap<>(0));
        this.returnMessage = returnMessage;
        this.returnStatus = returnStatus;
    }

    public WebApplicationException(String identification, String printMessage, HttpStatus returnStatus, Throwable cause) {
        this(identification, printMessage, null, returnStatus, cause);
    }

    public WebApplicationException(String identification, String printMessage, String returnMessage, Throwable cause) {
        this(identification, printMessage, returnMessage, null, cause);
    }

    public HttpStatus getReturnStatus() {
        return returnStatus;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnStatus(HttpStatus returnStatus) {
        this.returnStatus = returnStatus;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

}
