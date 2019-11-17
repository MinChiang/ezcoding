package com.ezcoding.web.resolver;

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
     * 代理模式设置
     * 若选择true，则使用代理对象，在进行值获取时才进行user反序列化操作
     * 若选择false，则在入参时直接进行反序列化
     *
     * @return 是否启用代理模式
     */
    CurrentUserTypeEnum type() default CurrentUserTypeEnum.AUTO;

    /**
     * 用户是否必须登录
     *
     * @return 用户是否必须登录
     */
    boolean required() default true;

}
