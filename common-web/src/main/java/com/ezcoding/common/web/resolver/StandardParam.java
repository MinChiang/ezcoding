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
     * 为json path路径解析
     * 若为空，则默认取对应参数名称的路径
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
     * 自动转化为参数类型的默认值
     * 支持类型：String、char、Character、boolean、Boolean、byte、Byte、short、Short、int、Integer、long、Long、float、Float、double、Double
     *
     * @return 默认参数
     */
    String defaultValue() default "";

}
