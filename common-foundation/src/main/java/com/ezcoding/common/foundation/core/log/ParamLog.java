package com.ezcoding.common.foundation.core.log;

import com.ezcoding.common.foundation.core.log.impl.DefaultLogParser;

import java.lang.annotation.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Inherited
public @interface ParamLog {

    /**
     * 计算表达式
     *
     * @return 表达式
     */
    String[] expressions() default "";

    /**
     * 格式化实现类
     *
     * @return 打印实现类
     */
    Class<? extends LogParser> parseClass() default DefaultLogParser.class;

}
