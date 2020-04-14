package com.ezcoding.common.foundation.core.message.head;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class ResponseAppHead extends AbstractAppHead implements Serializable {

    @JsonProperty(value = "returnCode")
    protected String returnCode;
    @JsonProperty(value = "returnMessage")
    protected List<String> returnMessages;

    public ResponseAppHead() {
    }

    public ResponseAppHead(PageInfo pageInfo) {
        super(pageInfo);
    }

    public ResponseAppHead(PageInfo pageInfo, String returnCode, List<String> returnMessages) {
        this.returnCode = returnCode;
        this.returnMessages = returnMessages;
        this.pageInfo = pageInfo;
    }

    public ResponseAppHead(String returnCode, List<String> returnMessages) {
        this(null, returnCode, returnMessages);
    }

    public ResponseAppHead(PageInfo pageInfo, String returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessages = Collections.singletonList(returnMessage);
        this.pageInfo = pageInfo;
    }

    public ResponseAppHead(String returnCode, String returnMessage) {
        this(null, returnCode, returnMessage);
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public List<String> getReturnMessages() {
        return returnMessages;
    }

    public void setReturnMessages(List<String> returnMessages) {
        this.returnMessages = returnMessages;
    }

}
