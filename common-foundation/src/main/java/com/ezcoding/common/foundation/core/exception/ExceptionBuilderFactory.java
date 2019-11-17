package com.ezcoding.common.foundation.core.exception;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-18 16:01
 */
public class ExceptionBuilderFactory<E extends AbstractApplicationException> {

    /**
     * 对应的错误类
     */
    private Class<E> e;

    /**
     * 错误详情码
     */
    private String detailCode;

    /**
     * 模板内容
     */
    private String template;

    private ExceptionBuilderFactory(Class<E> e, String detailCode, String template) {
        this.e = e;
        this.detailCode = detailCode;
        this.template = template;
    }

    /**
     * 创建构造器实例
     *
     * @param e          错误类型
     * @param detailCode 错误详情码
     * @param template   模板内容
     * @param <E>        错误类型
     * @return 构造器实例
     */
    public static <E extends AbstractApplicationException> ExceptionBuilderFactory<E> create(Class<E> e, String detailCode, String template) {
        return new ExceptionBuilderFactory<>(e, detailCode, template);
    }

    /**
     * 新建创建器
     *
     * @return 异常创建器
     */
    public ExceptionBuilder<E> instance() {
        return new ExceptionBuilder<>(this.e, this.detailCode, this.template);
    }

}
