package com.ezcoding.common.foundation.core.exception;

import org.springframework.context.MessageSource;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-29 10:48
 */
public class ModuleExceptionBuilderFactory extends BaseModuleExceptionBuilderFactory {

    private MessageSource messageSource;

    public ModuleExceptionBuilderFactory(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public MessageSourceTemplateExceptionBuilder messageSourceTemplateExceptionBuilder(IExceptionCodeGeneratable generator, String template, Object[] params) {
        return new MessageSourceTemplateExceptionBuilder(checkAndGenerate(generator), messageSource).template(template).params(params);
    }

    public MessageSourceTemplateExceptionBuilder messageSourceTemplateExceptionBuilder(TemplateExceptionCodeGenerator generator) {
        return new MessageSourceTemplateExceptionBuilder(checkAndGenerate(generator), messageSource).template(generator.getTemplate());
    }

}
