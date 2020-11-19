package com.ezcoding.common.foundation.core.log;

import com.ezcoding.common.foundation.core.log.impl.Slf4jLogPrinter;
import com.ezcoding.common.foundation.core.log.impl.StringLogFormatter;

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
    Class<? extends LogPrinter> logClass() default Slf4jLogPrinter.class;

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
