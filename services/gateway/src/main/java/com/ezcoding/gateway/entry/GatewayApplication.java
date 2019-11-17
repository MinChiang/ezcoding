package com.ezcoding.gateway.entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * 网关服务启动入口
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-20 11:02
 */
@SpringBootApplication
@EnableZuulProxy
@ComponentScan(basePackages = {"com.ezcoding.**.config"})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
