package com.ezcoding.base.web.extend.spring.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-22 9:07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggableMethod {

    Class<?> implementation();

}
