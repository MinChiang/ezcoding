package com.ezcoding.common.foundation.core.message.head;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
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
    protected List<String> returnMessage;

    public ResponseAppHead() {
    }

    public ResponseAppHead(PageInfo pageInfo) {
        super(pageInfo);
    }

    public ResponseAppHead(PageInfo pageInfo, String returnCode, List<String> returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.pageInfo = pageInfo;
    }

    public ResponseAppHead(String returnCode, List<String> returnMessage) {
        this(null, returnCode, returnMessage);
    }

    public ResponseAppHead(PageInfo pageInfo, String returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = new ArrayList<>();
        this.returnMessage.add(returnMessage);
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

    public List<String> getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(List<String> returnMessage) {
        this.returnMessage = returnMessage;
    }

}
