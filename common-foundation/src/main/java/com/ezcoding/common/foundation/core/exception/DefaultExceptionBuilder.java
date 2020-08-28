package com.ezcoding.common.foundation.core.exception;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-09 17:17
 */
public class DefaultExceptionBuilder extends AbstractExceptionBuilder {

    /**
     * 摘要
     */
    private final String summary;

    public DefaultExceptionBuilder(String identification, String summary) {
        super(identification);
        this.summary = summary;
    }

    @Override
    public String getSummary() {
        return this.summary;
    }

}
