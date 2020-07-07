package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;

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

    public TemplateExceptionCodeGenerator(ModuleLayerModule moduleLayerModule, String errorSuffixCode, String template) {
        super(moduleLayerModule, errorSuffixCode);
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

}
