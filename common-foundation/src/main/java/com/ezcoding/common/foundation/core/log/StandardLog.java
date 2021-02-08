package com.ezcoding.common.foundation.core.log;

import com.ezcoding.common.foundation.core.log.impl.DefaultLogFormatter;
import com.ezcoding.common.foundation.core.log.impl.DefaultLogPrinter;

import java.lang.annotation.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface StandardLog {

    /**
     * 打印实现类
     *
     * @return 打印实现类
     */
    Class<? extends LogPrinter> logClass() default DefaultLogPrinter.class;

    /**
     * 格式化实现类
     *
     * @return 打印实现类
     */
    Class<? extends LogFormatter> formatClass() default DefaultLogFormatter.class;

    /**
     * 表达式
     *
     * @return 表达式
     */
    String beforeExpression() default "";

    /**
     * 表达式
     *
     * @return 表达式
     */
    String afterExpression() default "";

    /**
     * 打印方式
     *
     * @return 打印方式
     * @see LogTypeEnum
     */
    LogTypeEnum type() default LogTypeEnum.SYNC;

    /**
     * 是否在返回表达式中用请求参数填充
     *
     * @return 是否在返回表达式中用请求参数填充
     */
    boolean fillParametersInReturn() default false;

}
