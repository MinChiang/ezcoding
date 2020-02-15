package com.ezcoding.common.foundation.core.exception;

/**
 * 业务定义异常
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-26 23:22
 */
public class ApplicationException extends RuntimeException implements IApplicationIdentifiable {

    /**
     * 错误标识
     */
    protected final String identification;

    public ApplicationException(String identification, String printMessage, Throwable cause) {
        super(printMessage, cause);
        this.identification = identification;
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
        return "\n发生异常：" +
                "\n\t指令代码：" +
                getIdentification() +
                "\n\t错误信息：" +
                getLocalizedMessage();
    }

}
