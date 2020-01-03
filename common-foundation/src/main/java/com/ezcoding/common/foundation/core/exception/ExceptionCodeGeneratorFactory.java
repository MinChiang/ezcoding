package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-29 11:08
 */
public class ExceptionCodeGeneratorFactory {

    public static ModuleExceptionCodeGenerator moduleExceptionCodeGenerator(ModuleExceptionIdentification moduleExceptionIdentification) {
        return moduleExceptionCodeGenerator(moduleExceptionIdentification.getModuleLayerModule(), moduleExceptionIdentification.getDetailCode());
    }

    public static ModuleExceptionCodeGenerator moduleExceptionCodeGenerator(ModuleLayerModule moduleLayerModule, String detailCode) {
        return new ModuleExceptionCodeGenerator(moduleLayerModule, detailCode);
    }

    public static TemplateExceptionCodeGenerator templateExceptionCodeGenerator(ModuleLayerModule moduleLayerModule, String detailCode, String template) {
        return new TemplateExceptionCodeGenerator(moduleLayerModule, detailCode, template);
    }

}
