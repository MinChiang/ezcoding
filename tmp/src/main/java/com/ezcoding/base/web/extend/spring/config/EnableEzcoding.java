package com.ezcoding.base.web.extend.spring.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:54
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EzcodingAutoConfiguration.class)
public @interface EnableEzcoding {

}
