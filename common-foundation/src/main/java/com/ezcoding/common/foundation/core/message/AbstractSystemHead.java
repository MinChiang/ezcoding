package com.ezcoding.common.foundation.core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class AbstractSystemHead implements SystemHead, Serializable {

    private static final long serialVersionUID = 9212375428640216675L;
    public static final String TRANSACTION_DATE = "transactionDate";
    public static final String VERSION = "version";
    public static final String DEFAULT_VERSION = "1.0";

    @JsonProperty(value = TRANSACTION_DATE)
    protected long transactionDate;
    @JsonProperty(value = VERSION)
    protected String version;

    public AbstractSystemHead() {
        this.transactionDate = System.currentTimeMillis();
        this.version = DEFAULT_VERSION;
    }

    @Override
    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
