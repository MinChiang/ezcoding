package com.ezcoding.test;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.application.constants.ModuleConstants;
import com.ezcoding.common.foundation.core.exception.AbstractApplicationException;
import com.ezcoding.common.foundation.core.exception.CommonApplicationException;
import com.ezcoding.common.foundation.core.exception.ExceptionBuilderFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static com.ezcoding.common.foundation.core.application.constants.ModuleConstants.DEFAULT_MODULE_LAYER_MODULE;
import static com.ezcoding.common.foundation.core.exception.CommonApplicationException.*;
import static com.ezcoding.test.ExceptionTest.TestException.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-28 15:17
 */
public class ExceptionTest {

    @Test
    public void lookupTest() {
        ExceptionBuilderFactory<CommonApplicationException> err1 = ExceptionBuilderFactory.lookupByAlias(CommonApplicationException.class, COMMON_PARAM_VALIDATE_ERROR);
        ExceptionBuilderFactory<?> err2 = ExceptionBuilderFactory.lookup(DEFAULT_MODULE_LAYER_MODULE, "1");
        ExceptionBuilderFactory<?> err3 = ExceptionBuilderFactory.lookup(DEFAULT_MODULE_LAYER_MODULE, "10");

        Assert.assertNotNull(err1);
        Assert.assertNotNull(err2);
        Assert.assertNull(err3);
    }

    @Test
    public void lookupAliasTest() {
        ExceptionBuilderFactory<CommonApplicationException> err1 = ExceptionBuilderFactory.lookupByAlias(CommonApplicationException.class, COMMON_PARAM_VALIDATE_ERROR);
        ExceptionBuilderFactory<CommonApplicationException> err2 = ExceptionBuilderFactory.lookupByAlias(CommonApplicationException.class, COMMON_REQUEST_TYPE_ERROR);
        ExceptionBuilderFactory<CommonApplicationException> err3 = ExceptionBuilderFactory.lookupByAlias(CommonApplicationException.class, COMMON_RESOURCE_NOT_FIND_ERROR);

        System.out.println(err1.instance().params("手机号").build().toString());
        System.out.println(err2.instance().cause(new IllegalArgumentException("请求必须传入对应的id")).build().toString());
        System.out.println(err3.instance().build().toString());
    }

    @Test
    public void lookupBuildersTest() {
        List<ExceptionBuilderFactory> factories = ExceptionBuilderFactory.lookupBuilders(DEFAULT_MODULE_LAYER_MODULE);

        Assert.assertEquals(factories.size(), 9);
        factories.forEach(System.out::println);
    }

    @Test
    public void lookupByErrorCodeTest() {
        ExceptionBuilderFactory<?> exceptionBuilderFactory = ExceptionBuilderFactory.lookupByErrorCode("0000000001");

        Assert.assertNotNull(exceptionBuilderFactory);
    }

    @Test
    public void registerTest() {
        ModuleLayerModule testModule = new ModuleLayerModule(ModuleConstants.DEFAULT_APPLICATION_LAYER_MODULE, "testModule", "2");
        ExceptionBuilderFactory.register(TestException.class, testModule, "1", "测试内容模板", TEST_ALIAS_1, TEST_ALIAS_2, TEST_ALIAS_3);

        ExceptionBuilderFactory<TestException> err1 = ExceptionBuilderFactory.lookupByAlias(TestException.class, TEST_ALIAS_1);
        ExceptionBuilderFactory<TestException> err2 = ExceptionBuilderFactory.lookupByAlias(TestException.class, TEST_ALIAS_2);
        ExceptionBuilderFactory<TestException> err3 = ExceptionBuilderFactory.lookupByAlias(TestException.class, TEST_ALIAS_3);
        System.out.println(err1.instance().build().toString());

        Assert.assertEquals(err1, err2);
        Assert.assertEquals(err2, err3);
    }

    @Test
    public void registerErrorTest() {
        ModuleLayerModule testModule = new ModuleLayerModule(ModuleConstants.DEFAULT_APPLICATION_LAYER_MODULE, "testModule", "2");
        ExceptionBuilderFactory.register(TestException.class, testModule, "4", "测试内容模板", TEST_ALIAS_4);

        boolean ef1 = false;
        try {
            ExceptionBuilderFactory.register(TestException.class, testModule, "5", "测试内容模板", TEST_ALIAS_4);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            ef1 = true;
        }
        Assert.assertTrue(ef1);

        boolean ef2 = false;
        try {
            ExceptionBuilderFactory.register(TestException.class, testModule, "4", "测试内容模板", TEST_ALIAS_5);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            ef2 = true;
        }
        Assert.assertTrue(ef2);
    }

    static class TestException extends AbstractApplicationException {

        public static final String TEST_ALIAS_1 = "TEST_ALIAS_1";
        public static final String TEST_ALIAS_2 = "TEST_ALIAS_2";
        public static final String TEST_ALIAS_3 = "TEST_ALIAS_3";
        public static final String TEST_ALIAS_4 = "TEST_ALIAS_4";
        public static final String TEST_ALIAS_5 = "TEST_ALIAS_5";

        public TestException(ModuleLayerModule moduleLayerModule, String detailCode, String message, Throwable cause) {
            super(moduleLayerModule, detailCode, message, cause);
        }

    }

}
