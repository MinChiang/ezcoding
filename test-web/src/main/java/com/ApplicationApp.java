package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 11:18
 */
@SpringBootApplication(scanBasePackages = {"com.test"})
public class ApplicationApp {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationApp.class, args);
    }

}
