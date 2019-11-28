package com.ezcoding.starter.foundation.core.exception;

import com.ezcoding.common.foundation.core.exception.IMessageObjectTranslatable;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-28 21:31
 */
public class MessageSourceTranslator implements IMessageObjectTranslatable {

    private MessageSource messageSource;

    public MessageSourceTranslator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String translate(String template, List<Object> params) {
        Object[] objects = null;
        if (params != null) {
            objects = params.stream().map(param -> messageSource.getMessage(param.toString(), null, Locale.SIMPLIFIED_CHINESE)).toArray();
        }
        return messageSource.getMessage(template, objects, Locale.SIMPLIFIED_CHINESE);
    }

}
