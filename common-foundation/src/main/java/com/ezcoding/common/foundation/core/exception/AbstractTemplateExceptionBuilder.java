package com.ezcoding.common.foundation.core.exception;

import com.google.common.collect.Lists;

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
    protected List<Object> params = Lists.newLinkedList();

    public AbstractTemplateExceptionBuilder(String identification) {
        super(identification);
    }

}
