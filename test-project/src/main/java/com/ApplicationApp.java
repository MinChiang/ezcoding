package com;

import com.ezcoding.starter.foundation.config.EnableEzcodingFoundation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 11:18
 */
@SpringBootApplication
@EnableEzcodingFoundation
public class ApplicationApp {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationApp.class, args);
    }

}
