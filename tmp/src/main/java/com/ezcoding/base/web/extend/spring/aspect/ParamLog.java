package com.ezcoding.base.web.extend.spring.aspect;

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

    String[] name();

    String[] expression();

}
