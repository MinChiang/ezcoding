package com.ezcoding.test;

import com.ezcoding.common.foundation.core.exception.ModuleExceptionIdentification;
import com.ezcoding.starter.foundation.core.exception.MessageSourceTemplateExceptionBuilder;
import com.ezcoding.starter.foundation.core.exception.ModuleExceptionBuilderFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ezcoding.starter.foundation.core.exception.ExceptionCodeGeneratorConstants.COMMON_NO_PERMISSION_ERROR;

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
        ModuleExceptionIdentification.setDefaultCodeLength(2);
        ModuleExceptionIdentification.setDefaultFillChar('_');

        ModuleExceptionBuilderFactory moduleExceptionBuilderFactory = new ModuleExceptionBuilderFactory(messageSource);
        MessageSourceTemplateExceptionBuilder messageSourceTemplateExceptionBuilder = moduleExceptionBuilderFactory.messageSourceTemplateExceptionBuilder(COMMON_NO_PERMISSION_ERROR, "user.template", "user.code", "user.account");
        System.out.println(messageSourceTemplateExceptionBuilder.build().toString());

    }

}
