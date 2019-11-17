package com.ezcoding.base.web.config;

import com.ezcoding.base.web.extend.spring.shutdown.ShutdownOperator;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-19 10:07
 */
@Configuration
public class ShutdownConfig {

    @Bean
    public ShutdownOperator shutdownOperator() {
        return new ShutdownOperator();
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(shutdownOperator());
        return tomcat;
    }

}
