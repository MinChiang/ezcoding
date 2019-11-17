package com.ezcoding.account.entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MinChiang
 * @version 1.0.0+
 * @date 2018-08-23 0:05
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.ezcoding.**.config"})
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

}
