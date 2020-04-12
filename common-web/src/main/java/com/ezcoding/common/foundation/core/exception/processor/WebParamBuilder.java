package com.ezcoding.common.foundation.core.exception.processor;

import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-12 20:33
 */
public class WebParamBuilder {

    /**
     * 默认的分割符号key参数
     */
    public static final String DEFAULT_SPLITTER = "DOT";

    /**
     * 国际化key
     */
    private List<String> codes = new ArrayList<>();

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
