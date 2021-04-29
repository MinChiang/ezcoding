package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-06 22:49
 */
public class SuccessAppHead extends ResponseAppHead implements Serializable {

    private static final long serialVersionUID = -3547811863595703652L;

    public static final String DEFAULT_SUCCESS_MESSAGE = "success";
    public static final String DEFAULT_SUCCESS_CODE = "0000000000";

    public SuccessAppHead() {
        this(null);
    }

    public SuccessAppHead(PageInfo pageInfo) {
        super(pageInfo, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public String toString() {
        return "SuccessAppHead{" +
                "pageInfo=" + pageInfo +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
