package com.ezcoding.common.foundation.core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class ResponseAppHead extends AbstractAppHead implements Serializable {

    private static final long serialVersionUID = 4369838938491033889L;

    @JsonProperty(value = "returnCode")
    protected String returnCode;
    @JsonProperty(value = "returnMessage")
    protected String returnMessage;

    public ResponseAppHead() {
    }

    public ResponseAppHead(PageInfo pageInfo) {
        super(pageInfo);
    }

    public ResponseAppHead(PageInfo pageInfo, String returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
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

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    /**
     * 判断是否成功
     *
     * @return 是否成功
     */
    public boolean success() {
        return Optional.ofNullable(this.returnCode).map(SuccessAppHead.getDefaultSuccessCode()::equals).orElse(false);
    }

    @Override
    public boolean valid() {
        return super.valid() && (returnCode != null && !returnCode.isEmpty()) && (returnMessage != null && !returnMessage.isEmpty());
    }

}
