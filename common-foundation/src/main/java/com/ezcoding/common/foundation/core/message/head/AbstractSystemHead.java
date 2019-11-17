package com.ezcoding.common.foundation.core.message.head;

import com.ezcoding.common.foundation.core.message.type.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class AbstractSystemHead implements IHead, Serializable {

    @JsonProperty(value = "terminalMac")
    protected String terminalMac;
    @JsonProperty(value = "transactionDate")
    protected long transactionDate;
    @JsonProperty(value = "md5")
    protected String md5;
    @JsonProperty(value = "transactionType")
    protected TransactionTypeEnum transactionType;
    @JsonProperty(value = "version")
    protected String version;

    public AbstractSystemHead() {
        transactionDate = System.currentTimeMillis();
        transactionType = TransactionTypeEnum.SYNCHRONIZE;
    }

    public String getTerminalMac() {
        return terminalMac;
    }

    public void setTerminalMac(String terminalMac) {
        this.terminalMac = terminalMac;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
