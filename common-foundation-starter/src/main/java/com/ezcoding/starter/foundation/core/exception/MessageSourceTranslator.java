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
    private LocaleResolvable localeResolvable = new DefaultLocaleResolver();

    public MessageSourceTranslator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public MessageSourceTranslator(MessageSource messageSource, LocaleResolvable localeResolvable) {
        this.messageSource = messageSource;
        this.localeResolvable = localeResolvable;
    }

    @Override
    public String translate(String template, List<Object> params) {
        Object[] objects = null;
        Locale locale = localeResolvable.resolve();
        if (params != null) {
            objects = params.stream().map(param -> messageSource.getMessage(param.toString(), null, locale)).toArray();
        }
        return messageSource.getMessage(template, objects, locale);
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public LocaleResolvable getLocaleResolvable() {
        return localeResolvable;
    }

    public void setLocaleResolvable(LocaleResolvable localeResolvable) {
        this.localeResolvable = localeResolvable;
    }

}
