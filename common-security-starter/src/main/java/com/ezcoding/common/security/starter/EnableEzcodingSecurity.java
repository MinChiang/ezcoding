package com.ezcoding.common.security.starter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-03-22 20:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({EzcodingSecurityAutoConfiguration.class})
public @interface EnableEzcodingSecurity {

}
