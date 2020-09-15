package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-06 22:49
 */
public class SuccessAppHead extends ResponseAppHead implements Serializable {

    private static final long serialVersionUID = -3547811863595703652L;

    protected static String defaultSuceessMessage = "success";
    protected static String defaultSuccessCode = "0000000000";

    public SuccessAppHead() {
        super(defaultSuccessCode, defaultSuceessMessage);
    }

    public SuccessAppHead(PageInfo pageInfo) {
        super(pageInfo, defaultSuccessCode, defaultSuceessMessage);
    }

    public static String getDefaultSuccessCode() {
        return defaultSuccessCode;
    }

    static void setDefaultSuccessCode(String defaultSuccessCode) {
        SuccessAppHead.defaultSuccessCode = defaultSuccessCode;
    }

    public static String getDefaultSuceessMessage() {
        return defaultSuceessMessage;
    }

    static void setDefaultSuceessMessage(String defaultSuceessMessage) {
        SuccessAppHead.defaultSuceessMessage = defaultSuceessMessage;
    }

}
