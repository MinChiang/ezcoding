package com.ezcoding.common.foundation.core.exception;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 上下文
     */
    private Map<String, Object> context = new HashMap<>(0);

    /**
     * 异常抛出内容
     */
    private Throwable cause;

    public AbstractExceptionBuilder(String identification) {
        this.identification = identification;
    }

    @Override
    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public ApplicationException build() {
        String summary = getSummary();
        String identification = getIdentification();
        return new ApplicationException(identification, summary, cause, this.context);
    }

    @Override
    public String getIdentification() {
        return this.identification;
    }

    @Override
    public Map<String, Object> setObject(String key, Object value) {
        context.put(key, value);
        return context;
    }

    @Override
    public Object getObject(String key) {
        return context.get(key);
    }

}
