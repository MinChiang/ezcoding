package com.ezcoding.common.security.annotation;

import java.lang.annotation.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-17 17:53
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DynamicSecured {

    /**
     * 加载别名
     *
     * @return 加载的别名
     */
    String alias() default "";

    /**
     * 对应的入口描述
     *
     * @return 入口描述
     */
    String description() default "";

}
