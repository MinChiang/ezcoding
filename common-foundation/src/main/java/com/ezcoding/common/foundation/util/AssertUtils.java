package com.ezcoding.common.foundation.util;

import com.ezcoding.common.foundation.core.exception.ApplicationException;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-29 13:36
 */
public class AssertUtils {

    /**
     * 校验生成器参数
     *
     * @param exceptionSupplier 断言表达式
     */
    private static void checkExceptionSupplier(Supplier<? extends ApplicationException> exceptionSupplier) {
        Objects.requireNonNull(exceptionSupplier, "exceptionSupplier can not be null");
    }

    /**
     * 断言表达式为true
     *
     * @param expression        需要断言的表达式
     * @param exceptionSupplier 异常提供器
     */
    public static void mustTrue(boolean expression, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        checkExceptionSupplier(exceptionSupplier);
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 断言表达式为false
     *
     * @param expression        需要断言的表达式
     * @param exceptionSupplier 异常提供器
     */
    public static void mustFalse(boolean expression, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        checkExceptionSupplier(exceptionSupplier);
        if (expression) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 断言两个对象相等
     *
     * @param o1                对象1
     * @param o2                对象2
     * @param exceptionSupplier 异常提供器
     */
    public static void mustEqual(Object o1, Object o2, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        checkExceptionSupplier(exceptionSupplier);
        if (!Objects.equals(o1, o2)) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 断言对象为null
     *
     * @param object            需要断言的对象
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNull(Object object, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        checkExceptionSupplier(exceptionSupplier);
        if (object != null) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 断言对象不能为null
     *
     * @param object            需要断言的对象
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNotNull(Object object, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        checkExceptionSupplier(exceptionSupplier);
        if (object == null) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 断言任意对象不能为null
     *
     * @param objects           需要断言的对象
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNotNull(Object[] objects, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        checkExceptionSupplier(exceptionSupplier);
        for (Object object : objects) {
            mustNotNull(object, exceptionSupplier);
        }
    }

    /**
     * 断言字符串为空
     *
     * @param charSequence      需要断言的字符串
     * @param exceptionSupplier 异常提供器
     */
    public static void mustEmpty(CharSequence charSequence, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        checkExceptionSupplier(exceptionSupplier);
        if (!(charSequence == null || charSequence.length() == 0)) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 断言字符串不能为空
     *
     * @param charSequence      需要断言的字符串
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNotEmpty(CharSequence charSequence, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        checkExceptionSupplier(exceptionSupplier);
        if (charSequence == null || charSequence.length() == 0) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 断言数组不能为空
     *
     * @param array             需要断言的数组
     * @param exceptionSupplier 异常提供器
     */
    public static <T> void mustNotEmpty(T[] array, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        checkExceptionSupplier(exceptionSupplier);
        if (array == null || array.length == 0) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 断言集合不能为空
     *
     * @param collection        需要断言的集合
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNotEmpty(Collection<?> collection, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        checkExceptionSupplier(exceptionSupplier);
        if (collection == null || collection.size() == 0) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 断言映射不能为空
     *
     * @param map               需要断言的映射
     * @param exceptionSupplier 异常提供器
     */
    public static void mustNotEmpty(Map<?, ?> map, Supplier<? extends ApplicationException> exceptionSupplier) throws ApplicationException {
        checkExceptionSupplier(exceptionSupplier);
        if (map == null || map.size() == 0) {
            throw exceptionSupplier.get();
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
        checkExceptionSupplier(exceptionSupplier);
        if (!type.isInstance(obj)) {
            throw exceptionSupplier.get();
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
        checkExceptionSupplier(exceptionSupplier);
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw exceptionSupplier.get();
        }
    }

}
