package com.ezcoding.starter.foundation.core.exception;

import com.ezcoding.common.foundation.core.exception.BaseModuleExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.exception.IExceptionCodeGeneratable;
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

    public MessageSourceTemplateExceptionBuilder messageSourceTemplateExceptionBuilder(IExceptionCodeGeneratable generator) {
        return new MessageSourceTemplateExceptionBuilder(checkAndGenerate(generator), messageSource);
    }

    public MessageSourceTemplateExceptionBuilder messageSourceTemplateExceptionBuilder(IExceptionCodeGeneratable generator, String template, Object... params) {
        return messageSourceTemplateExceptionBuilder(generator).template(template).params(params);
    }

}
