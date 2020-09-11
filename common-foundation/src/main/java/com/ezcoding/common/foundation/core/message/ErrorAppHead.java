package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-06 22:51
 */
public class ErrorAppHead extends ResponseAppHead implements Serializable {

    private static final long serialVersionUID = -1370948908683721517L;

    protected static String defaultErrorMessage = "发生未知错误";
    protected static String defaultErrorCode = "9999999999";

    public ErrorAppHead() {
        super(defaultErrorCode, defaultErrorMessage);
    }

    public ErrorAppHead(String returnCode, String returnMessage) {
        super(returnCode == null ? defaultErrorCode : returnCode, returnMessage == null ? defaultErrorMessage : returnMessage);
    }

    public static String getDefaultErrorCode() {
        return defaultErrorCode;
    }

    static void setDefaultErrorCode(String defaultErrorCode) {
        ErrorAppHead.defaultErrorCode = defaultErrorCode;
    }

    public static String getDefaultErrorMessage() {
        return defaultErrorMessage;
    }

    static void setDefaultErrorMessage(String defaultErrorMessage) {
        ErrorAppHead.defaultErrorMessage = defaultErrorMessage;
    }

}
