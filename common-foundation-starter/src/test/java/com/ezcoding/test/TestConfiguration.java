package com.ezcoding.test;

import com.ezcoding.common.foundation.starter.EzcodingFoundationAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-12 12:10
 */
@Configuration
@ComponentScan("com.ezcoding.test")
@Import({EzcodingFoundationAutoConfiguration.class})
@EnableAspectJAutoProxy
public class TestConfiguration {

}
