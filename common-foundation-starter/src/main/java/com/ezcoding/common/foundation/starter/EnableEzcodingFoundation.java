package com.ezcoding.common.foundation.starter;

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
@Import(EzcodingFoundationAutoConfiguration.class)
public @interface EnableEzcodingFoundation {

}
