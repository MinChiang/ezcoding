package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.BaseModuleExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.exception.IExceptionCodeGeneratable;
import com.ezcoding.common.foundation.core.exception.TemplateExceptionCodeGenerator;
import org.springframework.context.MessageSource;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-29 10:48
 */
public class ModuleExceptionBuilderFactory extends BaseModuleExceptionBuilderFactory {

    private static MessageSource messageSource;

    /**
     * 生成国际化的错误构造器
     *
     * @param generator 生成器
     * @param template  模板
     * @param params    参数
     * @return 国际化的错误构造器
     */
    public static MessageSourceTemplateExceptionBuilder messageSourceTemplateExceptionBuilder(IExceptionCodeGeneratable generator, String template, Object[] params) {
        return new MessageSourceTemplateExceptionBuilder(checkAndGenerate(generator), messageSource).template(template).params(params);
    }

    /**
     * 生成国际化的错误构造器
     *
     * @param generator 生成器
     * @return 国际化的错误构造器
     */
    public static MessageSourceTemplateExceptionBuilder messageSourceTemplateExceptionBuilder(TemplateExceptionCodeGenerator generator) {
        return new MessageSourceTemplateExceptionBuilder(checkAndGenerate(generator), messageSource).template(generator.getTemplate());
    }

    public static MessageSource getMessageSource() {
        return messageSource;
    }

    public static void setMessageSource(MessageSource messageSource) {
        ModuleExceptionBuilderFactory.messageSource = messageSource;
    }

}
