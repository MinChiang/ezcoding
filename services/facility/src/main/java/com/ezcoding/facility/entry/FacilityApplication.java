package com.ezcoding.facility.entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-20 11:02
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.ezcoding.**.config"})
public class FacilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacilityApplication.class, args);
    }

}
