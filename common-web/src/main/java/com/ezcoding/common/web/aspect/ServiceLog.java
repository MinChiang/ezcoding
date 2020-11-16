package com.ezcoding.common.web.aspect;

import com.ezcoding.common.web.aspect.impl.Slf4jLogger;
import com.ezcoding.common.web.aspect.impl.StringLogFormatter;

import java.lang.annotation.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface ServiceLog {

    /**
     * 打印实现类
     *
     * @return 打印实现类
     */
    Class<? extends ServiceLogger> logClass() default Slf4jLogger.class;

    /**
     * 格式化实现类
     *
     * @return 打印实现类
     */
    Class<? extends LogFormatter> formatClass() default StringLogFormatter.class;

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
     */
    LogTypeEnum type() default LogTypeEnum.SYNC;

}
