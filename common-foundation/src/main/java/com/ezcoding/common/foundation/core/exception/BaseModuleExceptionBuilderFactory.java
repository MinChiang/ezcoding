package com.ezcoding.common.foundation.core.exception;

import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-18 16:01
 */
public class BaseModuleExceptionBuilderFactory {

    /**
     * 判断生成器是否为空并生成唯一编号
     *
     * @param generator 唯一编号生成器
     * @return 生成的唯一编号
     */
    protected static String checkAndGenerate(IExceptionCodeGeneratable generator) {
        return Optional
                .of(generator)
                .map(IExceptionCodeGeneratable::generate)
                .get();
    }

    /**
     * 生成默认的错误的构造器
     *
     * @param generator 生成器
     * @param summary   错误描述
     * @return 默认的错误的构造器
     */
    public static DefaultExceptionBuilder defaultExceptionBuilder(IExceptionCodeGeneratable generator, String summary) {
        return new DefaultExceptionBuilder(checkAndGenerate(generator), summary);
    }

    /**
     * 生成可替换参数的错误构造器
     *
     * @param generator 生成器
     * @return 可替换参数的构造器
     */
    public static ParamTemplateExceptionBuilder paramTemplateExceptionBuilder(IExceptionCodeGeneratable generator) {
        return new ParamTemplateExceptionBuilder(checkAndGenerate(generator));
    }

    /**
     * 生成可替换参数的错误构造器
     *
     * @param generator 生成器
     * @param template  模板
     * @param params    替换参数
     * @return 可替换参数的构造器
     */
    public static ParamTemplateExceptionBuilder paramTemplateExceptionBuilder(IExceptionCodeGeneratable generator, String template, Object[] params) {
        return paramTemplateExceptionBuilder(generator).template(template).params(params);
    }

}
