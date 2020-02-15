package com.ezcoding.common.foundation.core.exception;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-28 22:48
 */
public class ParamTemplateExceptionBuilder extends AbstractTemplateExceptionBuilder {

    private static final String PREFIX = "{";
    private static final String SUFFIX = "}";

    public ParamTemplateExceptionBuilder(String identification) {
        super(identification);
    }

    @Override
    public String getSummary() {
        if (template == null || CollectionUtils.isEmpty(params)) {
            return template;
        }

        String temp = template;
        for (int i = 0; i < params.size(); i++) {
            temp = StringUtils.replace(temp, PREFIX + i + SUFFIX, params.get(i).toString());
        }
        return temp;
    }

    /**
     * 设定模板
     *
     * @param template 设定的模板
     * @return 实例对象
     */
    public ParamTemplateExceptionBuilder template(String template) {
        this.template = template;
        return this;
    }

    /**
     * 设定参数
     *
     * @param params 设定的参数
     * @return 实例对象
     */
    public ParamTemplateExceptionBuilder params(Object... params) {
        if (ArrayUtils.isEmpty(params)) {
            return this;
        }
        this.params.addAll(Arrays.asList(params));
        return this;
    }

}
