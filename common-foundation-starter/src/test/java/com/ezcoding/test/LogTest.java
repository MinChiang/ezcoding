package com.ezcoding.test;

import com.ezcoding.test.my.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-12 11:01
 */
@SpringBootTest(classes = TestConfiguration.class)
@RunWith(SpringRunner.class)
public class LogTest {

    @Autowired
    private TestService testService;

    @Test
    public void testLog() {
        TestService.Person jm = this.testService.service(new TestService.Person("jm", 27));
        System.out.println(jm);
    }

}
