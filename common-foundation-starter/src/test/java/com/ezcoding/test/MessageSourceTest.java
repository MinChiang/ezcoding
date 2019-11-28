package com.ezcoding.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-28 9:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationApp.class)
public class MessageSourceTest {

    @Autowired
    private MessageSource messageSource;

    @Test
    public void testMessageSource() {
        String message = messageSource.getMessage("user.code", null, Locale.SIMPLIFIED_CHINESE);
        System.out.println(message);
    }

}
