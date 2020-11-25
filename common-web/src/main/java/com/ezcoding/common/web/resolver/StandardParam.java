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
public @interface StandardParam {

    /**
     * 参数路径
     *
     * @return 参数路径
     */
    String value() default "";

    /**
     * 是否必须
     *
     * @return 是否必须
     */
    boolean required() default false;

    /**
     * 默认参数
     *
     * @return 默认参数
     */
    String defaultValue() default "";

}
