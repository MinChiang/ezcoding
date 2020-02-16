package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.AbstractTemplateExceptionBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-29 10:49
 */
public class MessageSourceTemplateExceptionBuilder extends AbstractTemplateExceptionBuilder {

    /**
     * 资源管理器
     */
    private MessageSource messageSource;

    /**
     * 当前的语言环境
     */
    private Locale locale = LocaleContextHolder.getLocale();

    public MessageSourceTemplateExceptionBuilder(String identification, MessageSource messageSource) {
        super(identification);
        this.messageSource = messageSource;
    }

    /**
     * 设定当前语言环境
     *
     * @param locale 当前的语言环境
     * @return 模板实例
     */
    public MessageSourceTemplateExceptionBuilder locale(Locale locale) {
        this.locale = locale;
        return this;
    }

    @Override
    public String getSummary() {
        if (template == null) {
            return null;
        }
        Object[] translate = null;
        if (CollectionUtils.isNotEmpty(params)) {
            translate = new Object[params.size()];
            for (int i = 0; i < params.size(); i++) {
                String p = params.get(i).toString();
                String message = messageSource.getMessage(p, null, p, locale);
                translate[i] = message;
            }
        }
        return messageSource.getMessage(template, translate, locale);
    }

    /**
     * 设定模板
     *
     * @param template 设定的模板
     * @return 实例对象
     */
    public MessageSourceTemplateExceptionBuilder template(String template) {
        this.template = template;
        return this;
    }

    /**
     * 设定参数
     *
     * @param params 设定的参数
     * @return 实例对象
     */
    public MessageSourceTemplateExceptionBuilder params(Object... params) {
        if (ArrayUtils.isEmpty(params)) {
            return this;
        }
        this.params.addAll(Arrays.asList(params));
        return this;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
