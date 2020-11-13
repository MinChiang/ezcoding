package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public abstract class AbstractSystemHead implements SystemHead, Serializable {

    private static final long serialVersionUID = 9212375428640216675L;

    public static final String DEFAULT_VERSION = "1.0";

    protected long transactionDate = System.currentTimeMillis();
    protected String version = DEFAULT_VERSION;

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

    @Override
    public boolean valid() {
        return transactionDate != 0 && version != null;
    }

    @Override
    public String toString() {
        return "AbstractSystemHead{" +
                "transactionDate=" + transactionDate +
                ", version='" + version + '\'' +
                '}';
    }

}
