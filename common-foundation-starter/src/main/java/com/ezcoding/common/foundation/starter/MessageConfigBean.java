package com.ezcoding.common.foundation.starter;

import static com.ezcoding.common.foundation.core.message.ErrorAppHead.getDefaultErrorCode;
import static com.ezcoding.common.foundation.core.message.ErrorAppHead.getDefaultErrorMessage;
import static com.ezcoding.common.foundation.core.message.SuccessAppHead.getDefaultSuccessCode;
import static com.ezcoding.common.foundation.core.message.SuccessAppHead.getDefaultSuccessMessage;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:12
 */
public class MessageConfigBean {

    private String errorResponseCode = getDefaultErrorCode();
    private String successResponseCode = getDefaultSuccessCode();
    private String errorResponseMessage = getDefaultErrorMessage();
    private String successResponseMessage = getDefaultSuccessMessage();

    public String getErrorResponseCode() {
        return errorResponseCode;
    }

    public void setErrorResponseCode(String errorResponseCode) {
        this.errorResponseCode = errorResponseCode;
    }

    public String getSuccessResponseCode() {
        return successResponseCode;
    }

    public void setSuccessResponseCode(String successResponseCode) {
        this.successResponseCode = successResponseCode;
    }

    public String getErrorResponseMessage() {
        return errorResponseMessage;
    }

    public void setErrorResponseMessage(String errorResponseMessage) {
        this.errorResponseMessage = errorResponseMessage;
    }

    public String getSuccessResponseMessage() {
        return successResponseMessage;
    }

    public void setSuccessResponseMessage(String successResponseMessage) {
        this.successResponseMessage = successResponseMessage;
    }

}
