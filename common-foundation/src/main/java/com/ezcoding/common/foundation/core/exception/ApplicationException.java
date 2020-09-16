package com.ezcoding.common.foundation.core.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务定义异常
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-26 23:22
 */
public class ApplicationException extends RuntimeException implements ApplicationIdentifiable {

    private static final long serialVersionUID = -4316549432937838305L;

    /**
     * 错误标识
     */
    protected final String identification;

    /**
     * 错误上下文
     */
    protected final Map<String, Object> context;

    public ApplicationException(String identification, String printMessage, Throwable cause, Map<String, Object> context) {
        super(printMessage, cause);
        this.identification = identification;
        this.context = context;
    }

    public ApplicationException(String identification, String printMessage, Throwable cause) {
        this(identification, printMessage, cause, new HashMap<>(0));
    }

    public Map<String, Object> getContext() {
        return context;
    }

    @Override
    public String getIdentification() {
        return this.identification;
    }

    @Override
    public String getSummary() {
        return getMessage();
    }

    @Override
    public String toString() {
        return "\nException occurs:" +
                "\n\tIdentification:" +
                getIdentification() +
                "\n\tError message:" +
                getLocalizedMessage();
    }

}
