package com.ezcoding.common.foundation.core.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-18 15:04
 */
public abstract class AbstractTemplateExceptionBuilder extends AbstractExceptionBuilder {

    /**
     * 模板内容
     */
    public static final String KEY_TEMPLATE = "template";

    /**
     * 参数内容
     */
    public static final String KEY_PARAMS = "params";

    public AbstractTemplateExceptionBuilder(String identification, String template) {
        this(identification, template, null);
    }

    public AbstractTemplateExceptionBuilder(String identification, String template, List<Object> params) {
        super(identification);
        setTemplate(Optional.of(template).get());
        setParams(params == null ? new ArrayList<>(0) : params);
    }

    public String getTemplate() {
        return getAndCastContextObject(KEY_TEMPLATE);
    }

    public void setTemplate(String template) {
        setObject(KEY_TEMPLATE, template);
    }

    public List<Object> getParams() {
        return getAndCastContextObject(KEY_PARAMS);
    }

    public void setParams(List<Object> params) {
        setObject(KEY_PARAMS, params);
    }

    /**
     * 添加参数
     *
     * @param params 被添加的参数
     */
    public void addParams(List<Object> params) {
        this.getParams().addAll(params);
    }

    /**
     * 添加参数
     *
     * @param params 被添加的参数
     */
    public void addParams(Object... params) {
        this.getParams().addAll(Arrays.asList(params));
    }

}
