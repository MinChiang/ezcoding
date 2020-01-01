package com.ezcoding.common.foundation.core.exception;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-28 21:13
 */
public abstract class AbstractExceptionBuilder implements IExceptionBuilder {

    /**
     * 错误编号
     */
    private String identification;

    public AbstractExceptionBuilder(String identification) {
        this.identification = identification;
    }

    @Override
    public ApplicationException build(Throwable cause) {
        String summary = getSummary();
        String identification = getIdentification();
        return new ApplicationException(identification, summary, cause);
    }

    @Override
    public ApplicationException build() {
        return build(null);
    }

    @Override
    public String getIdentification() {
        return this.identification;
    }

}
