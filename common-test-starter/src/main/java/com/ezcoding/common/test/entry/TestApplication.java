package com.ezcoding.common.test.entry;

import com.ezcoding.common.foundation.starter.EnableEzcodingFoundation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author MinChiang
 * @version 1.0.0+
 * @date 2018-08-23 0:05
 */
@EnableEzcodingFoundation
@SpringBootApplication(scanBasePackages = {"com.ezcoding"})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
