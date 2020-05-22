package com.ezcoding.common.foundation.core.message.head;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import static com.ezcoding.common.foundation.core.application.ModuleNameable.DETAIL_CODE_LENGHT;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-06 22:49
 */
public class SuccessAppHead extends ResponseAppHead implements Serializable {

    private static final long serialVersionUID = -3547811863595703652L;
    protected static String defaultSuceessMessage = "处理成功";
    protected static String defaultSuccessCode = StringUtils.repeat("0", DETAIL_CODE_LENGHT);

    public SuccessAppHead() {
        super(defaultSuccessCode, defaultSuceessMessage);
    }

    public SuccessAppHead(PageInfo pageInfo) {
        super(pageInfo, defaultSuccessCode, defaultSuceessMessage);
    }

    public static String getDefaultSuccessCode() {
        return defaultSuccessCode;
    }

    public static void setDefaultSuccessCode(String defaultSuccessCode) {
        SuccessAppHead.defaultSuccessCode = defaultSuccessCode;
    }

    public static String getDefaultSuceessMessage() {
        return defaultSuceessMessage;
    }

    public static void setDefaultSuceessMessage(String defaultSuceessMessage) {
        SuccessAppHead.defaultSuceessMessage = defaultSuceessMessage;
    }

}
