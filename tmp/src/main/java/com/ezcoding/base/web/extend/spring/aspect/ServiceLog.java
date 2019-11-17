package com.ezcoding.base.web.extend.spring.aspect;

import com.ezcoding.base.web.extend.spring.aspect.impl.Slf4jLogger;

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

    Class<?> implmentClass() default Slf4jLogger.class;

    String[] beforeExpression() default "";

    String[] afterExpression() default "";

}
