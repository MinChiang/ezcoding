package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-06 22:51
 */
public class ErrorAppHead extends ResponseAppHead implements Serializable {

    private static final long serialVersionUID = -1370948908683721517L;

    public static final String DEFAULT_ERROR_MESSAGE = "unknown error";
    public static final String DEFAULT_ERROR_CODE = "9999999999";

    public ErrorAppHead() {
        super(DEFAULT_ERROR_CODE, DEFAULT_ERROR_MESSAGE);
    }

    public ErrorAppHead(String code, String message) {
        super(code == null ? DEFAULT_ERROR_CODE : code, message == null ? DEFAULT_ERROR_MESSAGE : message);
    }

    @Override
    public String toString() {
        return "ErrorAppHead{" +
                "pageInfo=" + pageInfo +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
