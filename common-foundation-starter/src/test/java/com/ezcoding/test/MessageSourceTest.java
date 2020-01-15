package com.ezcoding.test;

import com.ezcoding.common.foundation.core.exception.MessageSourceTemplateExceptionBuilder;
import com.ezcoding.common.foundation.core.exception.ModuleExceptionBuilderFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.COMMON_NO_PERMISSION_ERROR;

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
    public void testMessageSourceTranslator() {
        ModuleExceptionBuilderFactory moduleExceptionBuilderFactory = new ModuleExceptionBuilderFactory(messageSource);
        MessageSourceTemplateExceptionBuilder messageSourceTemplateExceptionBuilder = moduleExceptionBuilderFactory.messageSourceTemplateExceptionBuilder(COMMON_NO_PERMISSION_ERROR);
        System.out.println(messageSourceTemplateExceptionBuilder.params("user.code", "user.account").build().toString());

    }

}
