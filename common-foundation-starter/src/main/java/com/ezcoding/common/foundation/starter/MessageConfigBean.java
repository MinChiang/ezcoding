package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.message.io.MessageIOFactory;

import static com.ezcoding.common.foundation.core.message.ErrorAppHead.getDefaultErrorCode;
import static com.ezcoding.common.foundation.core.message.ErrorAppHead.getDefaultErrorMessage;
import static com.ezcoding.common.foundation.core.message.PageInfo.getDefaultCurrentPage;
import static com.ezcoding.common.foundation.core.message.PageInfo.getDefaultPageSize;
import static com.ezcoding.common.foundation.core.message.SuccessAppHead.getDefaultSuccessCode;
import static com.ezcoding.common.foundation.core.message.SuccessAppHead.getDefaultSuceessMessage;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:12
 */
public class MessageConfigBean {

    private String readCharset = MessageIOFactory.DEFAULT_READ_CHARSET;
    private String writeCharset = MessageIOFactory.DEFAULT_WRITE_CHARSET;
    private String readMessageType = MessageIOFactory.DEFAULT_READ_MESSAGE_TYPE;
    private String writeMessageType = MessageIOFactory.DEFAULT_WRITE_MESSAGE_TYPE;

    private String errorResponseCode = getDefaultErrorCode();
    private String successResponseCode = getDefaultSuccessCode();
    private String errorResponseMessage = getDefaultErrorMessage();
    private String successResponseMessage = getDefaultSuceessMessage();

    private Integer currentPage = getDefaultCurrentPage();
    private Integer pageSize = getDefaultPageSize();

    public String getReadCharset() {
        return readCharset;
    }

    public void setReadCharset(String readCharset) {
        this.readCharset = readCharset;
    }

    public String getWriteCharset() {
        return writeCharset;
    }

    public void setWriteCharset(String writeCharset) {
        this.writeCharset = writeCharset;
    }

    public String getReadMessageType() {
        return readMessageType;
    }

    public void setReadMessageType(String readMessageType) {
        this.readMessageType = readMessageType;
    }

    public String getWriteMessageType() {
        return writeMessageType;
    }

    public void setWriteMessageType(String writeMessageType) {
        this.writeMessageType = writeMessageType;
    }

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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
