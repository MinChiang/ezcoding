package com.ezcoding.common.foundation.core.exception;

import java.util.Arrays;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-28 22:48
 */
public class ParamTemplateExceptionBuilder extends AbstractTemplateExceptionBuilder {

    private static final String PREFIX = "{";
    private static final String SUFFIX = "}";

    public ParamTemplateExceptionBuilder(String identification, String template) {
        super(identification, template);
    }

    @Override
    public String getSummary() {
        List<Object> params = this.getParams();
        String template = this.getTemplate();

        if (template == null || params.isEmpty()) {
            return template;
        }

        String temp = template;
        for (int i = 0; i < params.size(); i++) {
            temp = temp.replace(PREFIX + i + SUFFIX, params.get(i).toString());
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
        setTemplate(template);
        return this;
    }

    /**
     * 设定参数
     *
     * @param params 设定的参数
     * @return 实例对象
     */
    public ParamTemplateExceptionBuilder params(Object... params) {
        if (params == null || params.length == 0) {
            return this;
        }
        this.getParams().addAll(Arrays.asList(params));
        return this;
    }

}
