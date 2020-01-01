package com.ezcoding.common.foundation.core.exception;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-18 16:01
 */
public class BaseModuleExceptionBuilderFactory {

    protected String checkAndGenerate(IExceptionCodeGeneratable generator) {
        if (generator == null) {
            throw new RuntimeException("generator不能为空");
        }
        return generator.generate();
    }

    public DefaultExceptionBuilder defaultExceptionBuilder(IExceptionCodeGeneratable generator, String summary) {
        return new DefaultExceptionBuilder(checkAndGenerate(generator), summary);
    }

    public ParamTemplateExceptionBuilder paramTemplateExceptionBuilder(IExceptionCodeGeneratable generator) {
        return new ParamTemplateExceptionBuilder(checkAndGenerate(generator));
    }

    public ParamTemplateExceptionBuilder paramTemplateExceptionBuilder(IExceptionCodeGeneratable generator, String template, Object... params) {
        return paramTemplateExceptionBuilder(generator).template(template).params(params);
    }

}
