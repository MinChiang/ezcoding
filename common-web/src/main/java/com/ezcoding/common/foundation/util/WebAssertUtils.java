package com.ezcoding.common.foundation.util;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.exception.WebExceptionCodeGenerator;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-25 18:20
 */
public class WebAssertUtils {

    /**
     * 校验生成器参数
     *
     * @param generator 生成器参数
     */
    private static void checkGenerator(WebExceptionCodeGenerator generator) {
        Objects.requireNonNull(generator, "WebExceptionCodeGenerator can not be null");
    }

    /**
     * 断言表达式为true
     *
     * @param expression 需要断言的表达式
     * @param generator  异常提供器
     */
    public static void mustTrue(boolean expression, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustTrue(expression, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言表达式为false
     *
     * @param expression 需要断言的表达式
     * @param generator  异常提供器
     */
    public static void mustFalse(boolean expression, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustFalse(expression, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言两个对象相等
     *
     * @param o1        对象1
     * @param o2        对象2
     * @param generator 异常提供器
     */
    public static void mustEqual(Object o1, Object o2, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustEqual(o1, o2, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言对象为null
     *
     * @param object    需要断言的对象
     * @param generator 异常提供器
     */
    public static void mustNull(Object object, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustNull(object, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言对象不能为null
     *
     * @param object    需要断言的对象
     * @param generator 异常提供器
     */
    public static void mustNotNull(Object object, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustNotNull(object, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言任意对象不能为null
     *
     * @param objects   需要断言的对象
     * @param generator 异常提供器
     */
    public static void mustNotNull(Object[] objects, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustNotNull(objects, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言字符串为空
     *
     * @param text      需要断言的字符串
     * @param generator 异常提供器
     */
    public static void mustEmpty(String text, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustEmpty(text, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言字符串不能为空
     *
     * @param text      需要断言的字符串
     * @param generator 异常提供器
     */
    public static void mustNotEmpty(String text, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustNotEmpty(text, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言数组不能为空
     *
     * @param array     需要断言的数组
     * @param generator 异常提供器
     */
    public static <T> void mustNotEmpty(T[] array, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustNotEmpty(array, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言集合不能为空
     *
     * @param collection 需要断言的集合
     * @param generator  异常提供器
     */
    public static void mustNotEmpty(Collection<?> collection, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustNotEmpty(collection, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言映射不能为空
     *
     * @param map       需要断言的映射
     * @param generator 异常提供器
     */
    public static void mustNotEmpty(Map<?, ?> map, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustNotEmpty(map, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言对象是某个类的实例
     *
     * @param type      需要断言的类
     * @param obj       需要断言的实例
     * @param generator 异常提供器
     */
    public static void mustInstanceOf(Class<?> type, Object obj, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustInstanceOf(type, obj, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

    /**
     * 断言某个类是另外一个类的子类
     *
     * @param superType 需要断言的父类
     * @param subType   需要断言的子类
     * @param generator 异常提供器
     */
    public static void mustAssignable(Class<?> superType, Class<?> subType, WebExceptionCodeGenerator generator) throws ApplicationException {
        checkGenerator(generator);
        AssertUtils.mustAssignable(superType, subType, () -> WebExceptionBuilderFactory.webExceptionBuilder(generator).build());
    }

}
