package com.ezcoding.common.foundation.util;

import com.ezcoding.common.foundation.core.exception.ApplicationException;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-29 13:36
 */
public final class AssertUtils {

    /**
     * 断言表达式为true
     *
     * @param expression        需要断言的表达式
     * @param exceptionSupplier 异常提供器
     */
    public static void mustTrue(boolean expression, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        if (!expression) {
            throw Optional.of(exceptionSupplier).get().get();
        }
    }

    /**
     * 断言表达式为fasle
     *
     * @param expression        需要断言的表达式
     * @param exceptionSupplier 异常提供器
     */
    public static void mustFalse(boolean expression, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        if (expression) {
            throw Optional.of(exceptionSupplier).get().get();
        }
    }

    /**
     * 断言对象为null
     *
     * @param object            需要断言的对象
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNull(Object object, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        if (object != null) {
            throw Optional.of(exceptionSupplier).get().get();
        }
    }

    /**
     * 断言对象不能为null
     *
     * @param object            需要断言的对象
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNotNull(Object object, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        if (object == null) {
            throw Optional.of(exceptionSupplier).get().get();
        }
    }

    /**
     * 断言任意对象不能为null
     *
     * @param objects           需要断言的对象
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNotNull(Object[] objects, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        for (Object object : objects) {
            mustNotNull(object, exceptionSupplier);
        }
    }

    /**
     * 断言字符串为空
     *
     * @param text              需要断言的字符串
     * @param exceptionSupplier 异常提供器
     */
    public static void mustEmpty(String text, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        if (!(text == null || text.isEmpty())) {
            throw Optional.of(exceptionSupplier).get().get();
        }
    }

    /**
     * 断言字符串不能为空
     *
     * @param text              需要断言的字符串
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNotEmpty(String text, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        if (text == null || text.isEmpty()) {
            throw Optional.of(exceptionSupplier).get().get();
        }
    }

    /**
     * 断言数组不能为空
     *
     * @param array             需要断言的数组
     * @param exceptionSupplier 异常提供器
     */
    public static <T> void mustNotEmpty(T[] array, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        if (array == null || array.length == 0) {
            throw Optional.of(exceptionSupplier).get().get();
        }
    }

    /**
     * 断言集合不能为空
     *
     * @param collection        需要断言的集合
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNotEmpty(Collection<?> collection, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        if (collection == null || collection.size() == 0) {
            throw Optional.of(exceptionSupplier).get().get();
        }
    }

    /**
     * 断言映射不能为空
     *
     * @param map               需要断言的映射
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNotEmpty(Map<?, ?> map, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        if (map == null || map.size() == 0) {
            throw Optional.of(exceptionSupplier).get().get();
        }
    }

    /**
     * 断言对象是某个类的实例
     *
     * @param type              需要断言的类
     * @param obj               需要断言的实例
     * @param exceptionSupplier 异常提供器
     */
    public static void mustInstanceOf(Class<?> type, Object obj, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        if (!type.isInstance(obj)) {
            throw Optional.of(exceptionSupplier).get().get();
        }
    }

    /**
     * 断言某个类是另外一个类的子类
     *
     * @param superType         需要断言的父类
     * @param subType           需要断言的子类
     * @param exceptionSupplier 异常提供器
     */
    public static void mustAssignable(Class<?> superType, Class<?> subType, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw Optional.of(exceptionSupplier).get().get();
        }
    }

}
