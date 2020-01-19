package com.ezcoding.common.foundation.starter;

import lombok.Data;

import static com.ezcoding.common.foundation.core.message.builder.IMessageBuilder.*;
import static com.ezcoding.common.foundation.core.message.head.ErrorAppHead.getDefaultErrorCode;
import static com.ezcoding.common.foundation.core.message.head.ErrorAppHead.getDefaultErrorMessage;
import static com.ezcoding.common.foundation.core.message.head.PageInfo.getDefaultCurrentPage;
import static com.ezcoding.common.foundation.core.message.head.PageInfo.getDefaultPageSize;
import static com.ezcoding.common.foundation.core.message.head.SuccessAppHead.getDefaultSuccessCode;
import static com.ezcoding.common.foundation.core.message.head.SuccessAppHead.getDefaultSuceessMessage;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:12
 */
@Data
public class MessageConfigBean {

    private String readCharset = DEFAULT_READ_CHARSET;
    private String writeCharset = DEFAULT_WRITE_CHARSET;
    private String readMessageType = DEFAULT_READ_MESSAGE_TYPE;
    private String writeMessageType = DEFAULT_WRITE_MESSAGE_TYPE;

    private String errorResponseCode = getDefaultErrorCode();
    private String successResponseCode = getDefaultSuccessCode();
    private String errorResponseMessage = getDefaultErrorMessage();
    private String successResponseMessage = getDefaultSuceessMessage();

    private Integer currentPage = getDefaultCurrentPage();
    private Integer pageSize = getDefaultPageSize();

}
