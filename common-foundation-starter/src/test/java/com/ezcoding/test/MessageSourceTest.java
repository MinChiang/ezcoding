package com.ezcoding.test;

import com.ezcoding.common.foundation.core.application.constants.ModuleConstants;
import com.ezcoding.common.foundation.core.exception.CommonApplicationException;
import com.ezcoding.common.foundation.core.exception.ExceptionBuilderFactory;
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
        String message = messageSource.getMessage("notNull", new Object[]{"user.code"}, Locale.SIMPLIFIED_CHINESE);
        System.out.println(message);
    }

    @Test
    public void testMessageSourceTranslator() {
        CommonApplicationException build = ExceptionBuilderFactory
                .register(CommonApplicationException.class, ModuleConstants.DEFAULT_MODULE_LAYER_MODULE, "10", "notNull", "haha")
                .instance()
                .params("user.code", "user.account")
                .build();

        System.out.println(build.toString());
    }

}
