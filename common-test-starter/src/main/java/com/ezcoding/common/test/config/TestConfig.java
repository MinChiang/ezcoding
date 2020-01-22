package com.ezcoding.common.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-20 14:12
 */
@Configuration
public class TestConfig {

    @Bean
    public TestApplicationExceptionProcessorConfigurer testApplicationExceptionProcessorRegister() {
        return new TestApplicationExceptionProcessorConfigurer();
    }

}
