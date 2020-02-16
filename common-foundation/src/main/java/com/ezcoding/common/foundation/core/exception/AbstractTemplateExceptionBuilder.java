package com.ezcoding.common.foundation.core.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-18 15:04
 */
public abstract class AbstractTemplateExceptionBuilder extends AbstractExceptionBuilder {

    /**
     * 模板内容
     */
    protected String template;

    /**
     * 参数内容
     */
    protected List<Object> params = new ArrayList<>(0);

    public AbstractTemplateExceptionBuilder(String identification) {
        super(identification);
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    /**
     * 添加参数
     *
     * @param params 被添加的参数
     */
    public void addParams(List<Object> params) {
        this.params.addAll(params);
    }

    /**
     * 添加参数
     *
     * @param params 被添加的参数
     */
    public void addParams(Object[] params) {
        this.params.addAll(Arrays.asList(params));
    }

}
