package com.ezcoding.common.foundation.util;

import com.ezcoding.common.foundation.core.exception.AbstractApplicationException;
import com.ezcoding.common.foundation.core.exception.ExceptionBuilderFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-29 13:36
 */
public final class AssertUtils {

    /**
     * 断言表达式为true
     *
     * @param expression 需要断言的表达式
     * @param exception  断言业务异常
     */
    public static void mustTrue(boolean expression, AbstractApplicationException exception) {
        if (!expression) {
            throw exception;
        }
    }

    /**
     * 断言表达式为fasle
     *
     * @param expression 需要断言的表达式
     * @param exception  断言业务异常
     */
    public static void mustFalse(boolean expression, AbstractApplicationException exception) {
        if (expression) {
            throw exception;
        }
    }

    /**
     * 断言对象为null
     *
     * @param object    需要断言的对象
     * @param exception 断言业务异常
     */
    public static void mustNull(Object object, AbstractApplicationException exception) {
        if (object != null) {
            throw exception;
        }
    }

    /**
     * 断言对象不能为null
     *
     * @param object    需要断言的对象
     * @param exception 断言业务异常
     */
    public static void mustNotNull(Object object, AbstractApplicationException exception) {
        if (object == null) {
            throw exception;
        }
    }

    /**
     * 断言字符串为空
     *
     * @param text      需要断言的字符串
     * @param exception 断言业务异常
     */
    public static void mustEmpty(String text, AbstractApplicationException exception) {
        if (StringUtils.isNotEmpty(text)) {
            throw exception;
        }
    }

    /**
     * 断言字符串不能为空
     *
     * @param text      需要断言的字符串
     * @param exception 断言业务异常
     */
    public static void mustNotEmpty(String text, AbstractApplicationException exception) {
        if (StringUtils.isEmpty(text)) {
            throw exception;
        }
    }

    /**
     * 断言数组不能为空
     *
     * @param array     需要断言的数组
     * @param exception 断言业务异常
     */
    public static void mustNotEmpty(Object[] array, AbstractApplicationException exception) {
        if (ArrayUtils.isEmpty(array)) {
            throw exception;
        }
    }

    /**
     * 断言集合不能为空
     *
     * @param collection 需要断言的集合
     * @param exception  断言业务异常
     */
    public static void mustNotEmpty(Collection<?> collection, AbstractApplicationException exception) {
        if (CollectionUtils.isEmpty(collection)) {
            throw exception;
        }
    }

    /**
     * 断言映射不能为空
     *
     * @param map       需要断言的映射
     * @param exception 断言业务异常
     */
    public static void mustNotEmpty(Map<?, ?> map, AbstractApplicationException exception) {
        if (MapUtils.isEmpty(map)) {
            throw exception;
        }
    }

    /**
     * 断言对象是某个类的实例
     *
     * @param type      需要断言的类
     * @param obj       需要断言的实例
     * @param exception 断言业务异常
     */
    public static void mustInstanceOf(Class<?> type, Object obj, AbstractApplicationException exception) {
        if (!type.isInstance(obj)) {
            throw exception;
        }
    }

    /**
     * 断言某个类是另外一个类的子类
     *
     * @param superType 需要断言的父类
     * @param subType   需要断言的子类
     * @param exception 断言业务异常
     */
    public static void mustAssignable(Class<?> superType, Class<?> subType, AbstractApplicationException exception) {
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw exception;
        }
    }

    /**
     * 断言表达式为true
     *
     * @param expression 需要断言的表达式
     * @param factory    断言业务异常
     */
    public static void mustTrue(boolean expression, ExceptionBuilderFactory factory) {
        if (!expression) {
            throw factory.instance().build();
        }
    }

    /**
     * 断言表达式为fasle
     *
     * @param expression 需要断言的表达式
     * @param factory    断言业务异常
     */
    public static void mustFalse(boolean expression, ExceptionBuilderFactory factory) {
        if (expression) {
            throw factory.instance().build();
        }
    }

    /**
     * 断言对象为null
     *
     * @param object  需要断言的对象
     * @param factory 断言业务异常
     */
    public static void mustNull(Object object, ExceptionBuilderFactory factory) {
        if (object != null) {
            throw factory.instance().build();
        }
    }

    /**
     * 断言对象不能为null
     *
     * @param object  需要断言的对象
     * @param factory 断言业务异常
     */
    public static void mustNotNull(Object object, ExceptionBuilderFactory factory) {
        if (object == null) {
            throw factory.instance().build();
        }
    }

    /**
     * 断言字符串为空
     *
     * @param text    需要断言的字符串
     * @param factory 断言业务异常
     */
    public static void mustEmpty(String text, ExceptionBuilderFactory factory) {
        if (StringUtils.isNotEmpty(text)) {
            throw factory.instance().build();
        }
    }

    /**
     * 断言字符串不能为空
     *
     * @param text    需要断言的字符串
     * @param factory 断言业务异常
     */
    public static void mustNotEmpty(String text, ExceptionBuilderFactory factory) {
        if (StringUtils.isEmpty(text)) {
            throw factory.instance().build();
        }
    }

    /**
     * 断言数组不能为空
     *
     * @param array   需要断言的数组
     * @param factory 断言业务异常
     */
    public static void mustNotEmpty(Object[] array, ExceptionBuilderFactory factory) {
        if (ArrayUtils.isEmpty(array)) {
            throw factory.instance().build();
        }
    }

    /**
     * 断言集合不能为空
     *
     * @param collection 需要断言的集合
     * @param factory    断言业务异常
     */
    public static void mustNotEmpty(Collection<?> collection, ExceptionBuilderFactory factory) {
        if (CollectionUtils.isEmpty(collection)) {
            throw factory.instance().build();
        }
    }

    /**
     * 断言映射不能为空
     *
     * @param map     需要断言的映射
     * @param factory 断言业务异常
     */
    public static void mustNotEmpty(Map<?, ?> map, ExceptionBuilderFactory factory) {
        if (MapUtils.isEmpty(map)) {
            throw factory.instance().build();
        }
    }

    /**
     * 断言对象是某个类的实例
     *
     * @param type    需要断言的类
     * @param obj     需要断言的实例
     * @param factory 断言业务异常
     */
    public static void mustInstanceOf(Class<?> type, Object obj, ExceptionBuilderFactory factory) {
        if (!type.isInstance(obj)) {
            throw factory.instance().build();
        }
    }

    /**
     * 断言某个类是另外一个类的子类
     *
     * @param superType 需要断言的父类
     * @param subType   需要断言的子类
     * @param factory   断言业务异常
     */
    public static void mustAssignable(Class<?> superType, Class<?> subType, ExceptionBuilderFactory factory) {
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw factory.instance().build();
        }
    }

}
