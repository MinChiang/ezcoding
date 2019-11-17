package com.ezcoding.base.web.extend.spring.config;

import lombok.Data;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:12
 */
@Data
public class MessageConfigBean {

    private String readCharset = "UTF-8";
    private String writeCharset = "UTF-8";
    private String readMessageType = "JSON";
    private String writeMessageType = "JSON";

    private String errorResponseCode = "99999999";
    private String successResponseCode = "00000000";
    private String errorResponseMessage = "发生未知错误";
    private String successResponseMessage = "处理成功";

    private Integer pageSize = 10;
    private Integer currentPage = 1;

}
