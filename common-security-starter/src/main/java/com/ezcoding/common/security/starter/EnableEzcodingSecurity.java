package com.ezcoding.common.security.starter;

import com.ezcoding.common.security.advice.SecurityAdviceConfiguration;
import com.ezcoding.common.web.starter.EnableEzcodingWeb;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableEzcodingWeb
@Import({MethodSecurityAutoConfiguration.class, ResourceServerAutoConfiguration.class, SecurityAdviceConfiguration.class, SecurityCommonConfiguration.class})
@EnableConfigurationProperties(EzcodingSecurityConfigBean.class)
public @interface EnableEzcodingSecurity {

}
