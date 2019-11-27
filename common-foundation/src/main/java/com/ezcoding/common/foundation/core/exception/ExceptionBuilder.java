package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-18 15:04
 */
public class ExceptionBuilder<E extends AbstractApplicationException> {

    private static IMessageObjectTranslatable translator = new EmptyTranslator();

    /**
     * 对应的错误类
     */
    private Class<E> e;

    /**
     * 错误所属模块
     */
    private ModuleLayerModule moduleLayerModule;

    /**
     * 错误详情码
     */
    private String detailCode;

    /**
     * 模板内容
     */
    private String template;

    /**
     * 参数内容
     */
    private List<Object> params;

    /**
     * 错误原因
     */
    private Throwable cause;

    ExceptionBuilder(Class<E> e, ModuleLayerModule moduleLayerModule, String detailCode, String template) {
        this.e = e;
        this.moduleLayerModule = moduleLayerModule;
        this.detailCode = detailCode;
        this.template = template;
    }

    /**
     * 配置模板参数翻译器
     *
     * @param translatable 翻译器
     */
    public static void configTranslator(IMessageObjectTranslatable translatable) {
        translator = translatable;
    }

    private void initParasKeeper() {
        this.params = new ArrayList<>();
    }

    /**
     * 设定参数
     *
     * @param params 参数内容
     * @return 构造器实例
     */
    public ExceptionBuilder<E> params(Object... params) {
        if (ArrayUtils.isEmpty(params)) {
            return this;
        }

        if (this.params == null) {
            this.initParasKeeper();
        }

        this.params.addAll(Arrays.asList(params));
        return this;
    }

    /**
     * 设定参数
     *
     * @param param 参数内容
     * @return 构造器实例
     */
    public ExceptionBuilder<E> param(Object param) {
        if (param == null) {
            return this;
        }

        if (this.params == null) {
            this.initParasKeeper();
        }

        this.params.add(param);
        return this;
    }

    /**
     * 设定错误原因
     *
     * @param cause 错误原因
     * @return 构造器实例
     */
    public ExceptionBuilder<E> cause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    /**
     * 创建错误异常
     *
     * @return 错误异常实例
     */
    public E build() {
        try {
            String translate = translator.translate(template, this.params);
            Constructor<E> constructor = this.e.getDeclaredConstructor(ModuleLayerModule.class, String.class, String.class, Throwable.class);
            constructor.setAccessible(true);
            return constructor.newInstance(this.moduleLayerModule, this.detailCode, translate, this.cause);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
