package com.ezcoding.starter.foundation.config;

import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import com.ezcoding.common.foundation.core.message.head.ErrorAppHead;
import com.ezcoding.common.foundation.core.message.head.PageInfo;
import com.ezcoding.common.foundation.core.message.head.SuccessAppHead;
import lombok.Data;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:12
 */
@Data
public class MessageConfigBean {

    private String readCharset = IMessageBuilder.DEFAULT_READ_CHARSET;
    private String writeCharset = IMessageBuilder.DEFAULT_WRITE_CHARSET;
    private String readMessageType = IMessageBuilder.DEFAULT_READ_MESSAGE_TYPE;
    private String writeMessageType = IMessageBuilder.DEFAULT_WRITE_MESSAGE_TYPE;

    private String errorResponseCode = ErrorAppHead.getDefaultErrorCode();
    private String successResponseCode = SuccessAppHead.getDefaultSuccessCode();
    private String errorResponseMessage = ErrorAppHead.getDefaultErrorMessage();
    private String successResponseMessage = SuccessAppHead.getDefaultSuceessMessage();

    private Integer currentPage = PageInfo.CURRENT_PAGE;
    private Integer pageSize = PageInfo.PAGE_SIZE;

}
