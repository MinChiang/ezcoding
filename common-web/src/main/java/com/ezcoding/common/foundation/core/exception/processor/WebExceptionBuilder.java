package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.AbstractTemplateExceptionBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    public WebExceptionBuilder(String identification, String template, MessageSource messageSource, Locale locale) {
        super(identification, template);
        this.setMessageSource(messageSource);
        this.setLocale(locale);
    }

    public WebExceptionBuilder(String identification, String template, MessageSource messageSource) {
        this(identification, template, messageSource, LocaleContextHolder.getLocale());
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
        if (params != null && !params.isEmpty()) {
            translate = new Object[params.size()];
            for (int i = 0; i < params.size(); i++) {
                Object o = params.get(i);
                String code;
                if (o instanceof WebParamBuilder) {
                    code = ((WebParamBuilder) o).build(messageSource, locale);
                } else if (o instanceof OriginalParamBuilder) {
                    code = ((OriginalParamBuilder) o).getObject().toString();
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

    /**
     * 添加参数
     *
     * @param params 被添加的参数
     */
    public WebExceptionBuilder addOriginalParams(Object... params) {
        if (params == null || params.length == 0) {
            return this;
        }
        addParams(Arrays.stream(params).map(OriginalParamBuilder::new));
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

    /**
     * @author MinChiang
     * @version 1.0.0
     * @date 2020-04-12 20:33
     */
    public static class WebParamBuilder {

        /**
         * 默认的分割符号key参数
         */
        public static final String DEFAULT_SPLITTER = "DOT";

        /**
         * 国际化key
         */
        private final List<String> codes = new ArrayList<>();

        /**
         * 分割符号key
         */
        private String splitter = DEFAULT_SPLITTER;

        /**
         * 构建单个参数
         *
         * @param messageSource 国际化配置
         * @return 构建完成的单个参数
         */
        String build(MessageSource messageSource, Locale locale) {
            if (codes.isEmpty()) {
                return null;
            }

            String split = messageSource.getMessage(this.splitter, null, this.splitter, locale);
            if (split == null) {
                throw new IllegalArgumentException("未配置对应的参数分隔符（DOT）");
            }

            return codes
                    .stream()
                    .map(code -> messageSource.getMessage(code, null, code, locale))
                    .collect(Collectors.joining(split));
        }

        /**
         * 设置分割符号key
         *
         * @param split 分割符号
         * @return 对应的参数构造器
         */
        public WebParamBuilder splitter(String split) {
            this.splitter = split;
            return this;
        }

        /**
         * 增加模板参数
         *
         * @param codes 模板参数
         * @return 对应的参数构造器
         */
        public WebParamBuilder codes(String... codes) {
            this.codes.addAll(Arrays.asList(codes));
            return this;
        }

    }

    /**
     * @author MinChiang
     * @version 1.0.0
     * @date 2020-04-12 20:33
     */
    public static class OriginalParamBuilder {

        private final Object object;

        public OriginalParamBuilder(Object object) {
            this.object = object;
        }

        public Object getObject() {
            return object;
        }

    }

}
