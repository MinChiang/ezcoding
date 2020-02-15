package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-02 14:21
 */
public class TemplateExceptionCodeGenerator extends ModuleExceptionCodeGenerator {

    /**
     * 模板名称
     */
    private final String template;

    public TemplateExceptionCodeGenerator(FunctionLayerModule functionLayerModule, String template) {
        super(functionLayerModule);
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

}
