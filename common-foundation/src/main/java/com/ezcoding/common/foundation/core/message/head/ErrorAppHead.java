package com.ezcoding.common.foundation.core.message.head;

import java.io.Serializable;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-06 22:51
 */
public class ErrorAppHead extends ResponseAppHead implements Serializable {

    protected static String defaultErrorCode = "99999999";
    protected static String defaultErrorMessage = "发生未知错误";

    public ErrorAppHead() {
        super(defaultErrorCode, defaultErrorMessage);
    }

    public ErrorAppHead(String returnCode, String returnMessage) {
        super(returnCode, returnMessage);
    }

    public ErrorAppHead(String returnCode, List<String> returnMessage) {
        super(returnCode, returnMessage);
    }

    public static String getDefaultErrorCode() {
        return defaultErrorCode;
    }

    public static void setDefaultErrorCode(String defaultErrorCode) {
        ErrorAppHead.defaultErrorCode = defaultErrorCode;
    }

    public static String getDefaultErrorMessage() {
        return defaultErrorMessage;
    }

    public static void setDefaultErrorMessage(String defaultErrorMessage) {
        ErrorAppHead.defaultErrorMessage = defaultErrorMessage;
    }
}
