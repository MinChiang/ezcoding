package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.AbstractTemplateExceptionBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Locale;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-29 10:49
 */
public class WebExceptionBuilder extends AbstractTemplateExceptionBuilder {

    /**
     * 资源管理器
     */
    public static final String KEY_MESSAGE = "messageSource";

    /**
     * 当前的语言环境
     */
    public static final String KEY_LOCALE = "locale";

    /**
     * 错误响应状态码
     */
    public static final String KEY_HTTP_STATUS = "httpStatus";

    public WebExceptionBuilder(String identification, String template, MessageSource messageSource) {
        super(identification, template);
        this.setMessageSource(messageSource);
        this.setLocale(LocaleContextHolder.getLocale());
    }

    @Override
    public String getSummary() {
        List<Object> params = this.getParams();
        String template = this.getTemplate();
        Locale locale = this.getLocale();
        MessageSource messageSource = this.getMessageSource();

        if (template == null) {
            return null;
        }
        Object[] translate = null;
        if (CollectionUtils.isNotEmpty(params)) {
            translate = new Object[params.size()];
            for (int i = 0; i < params.size(); i++) {
                Object o = params.get(i);
                String code;
                if (o instanceof WebParamBuilder) {
                    code = ((WebParamBuilder) o).build(messageSource, locale);
                } else {
                    code = o.toString();
                }

                if (code != null) {
                    translate[i] = messageSource.getMessage(code, null, code, locale);
                }
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
    public WebExceptionBuilder template(String template) {
        setTemplate(template);
        return this;
    }

    /**
     * 设定当前语言环境
     *
     * @param locale 当前的语言环境
     * @return 模板实例
     */
    public WebExceptionBuilder locale(Locale locale) {
        this.setLocale(locale);
        return this;
    }

    /**
     * 设定参数
     *
     * @param params 设定的参数
     * @return 实例对象
     */
    public WebExceptionBuilder params(Object... params) {
        addParams(params);
        return this;
    }

    /**
     * 设置错误返回状态码
     *
     * @param httpStatus 错误状态返回码
     * @return 实例对象
     */
    public WebExceptionBuilder httpStatus(HttpStatus httpStatus) {
        setHttpStatus(httpStatus);
        return this;
    }

    /**
     * 添加参数
     *
     * @param webParamBuilders 被添加的参数
     */
    public WebExceptionBuilder addParams(WebParamBuilder... webParamBuilders) {
        addParams((Object[]) webParamBuilders);
        return this;
    }

    public MessageSource getMessageSource() {
        return getAndCastContextObject(KEY_MESSAGE);
    }

    public void setMessageSource(MessageSource messageSource) {
        setObject(KEY_MESSAGE, messageSource);
    }

    public Locale getLocale() {
        return getAndCastContextObject(KEY_LOCALE);
    }

    public void setLocale(Locale locale) {
        setObject(KEY_LOCALE, locale);
    }

    public HttpStatus getHttpStatus() {
        return getAndCastContextObject(KEY_HTTP_STATUS);
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        setObject(KEY_HTTP_STATUS, httpStatus);
    }

}
