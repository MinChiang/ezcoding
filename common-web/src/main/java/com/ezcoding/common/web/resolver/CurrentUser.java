package com.ezcoding.common.web.resolver;

import java.lang.annotation.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-04 10:44
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CurrentUser {

    /**
     * 详见required()方法
     *
     * @return 用户是否必须登录
     */
    boolean value() default true;

    /**
     * 代理模式设置
     *
     * @return 代理模式
     */
    CurrentUserTypeEnum type() default CurrentUserTypeEnum.AUTO;

    /**
     * 用户是否必须登录
     *
     * @return 用户是否必须登录
     */
    boolean required() default true;

}
