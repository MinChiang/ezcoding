package com.ezcoding.common.web.resolver;

import java.lang.annotation.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-21 16:25
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface JsonParam {

    String value() default "";

    boolean required() default false;

    String defaultValue() default "";

}
