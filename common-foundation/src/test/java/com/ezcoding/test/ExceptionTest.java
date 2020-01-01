package com.ezcoding.test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-28 15:17
 */
public class ExceptionTest {

//    @Test
//    public void lookupTest() {
//        ModuleExceptionBuilderFactory<CommonApplicationException> err1 = ModuleExceptionBuilderFactory.lookupByAlias(CommonApplicationException.class, COMMON_PARAM_VALIDATE_ERROR);
//        ModuleExceptionBuilderFactory<?> err2 = ModuleExceptionBuilderFactory.lookup(DEFAULT_MODULE_LAYER_MODULE, "1");
//        ModuleExceptionBuilderFactory<?> err3 = ModuleExceptionBuilderFactory.lookup(DEFAULT_MODULE_LAYER_MODULE, "10");
//
//        Assert.assertNotNull(err1);
//        Assert.assertNotNull(err2);
//        Assert.assertNull(err3);
//    }
//
//    @Test
//    public void lookupAliasTest() {
//        ModuleExceptionBuilderFactory<CommonApplicationException> err1 = ModuleExceptionBuilderFactory.lookupByAlias(CommonApplicationException.class, COMMON_PARAM_VALIDATE_ERROR);
//        ModuleExceptionBuilderFactory<CommonApplicationException> err2 = ModuleExceptionBuilderFactory.lookupByAlias(CommonApplicationException.class, COMMON_REQUEST_TYPE_ERROR);
//        ModuleExceptionBuilderFactory<CommonApplicationException> err3 = ModuleExceptionBuilderFactory.lookupByAlias(CommonApplicationException.class, COMMON_RESOURCE_NOT_FIND_ERROR);
//
//        System.out.println(err1.instance().params("手机号").build().toString());
//        System.out.println(err2.instance().cause(new IllegalArgumentException("请求必须传入对应的id")).build().toString());
//        System.out.println(err3.instance().build().toString());
//    }
//
//    @Test
//    public void lookupBuildersTest() {
//        List<ModuleExceptionBuilderFactory> factories = ModuleExceptionBuilderFactory.lookupBuilders(DEFAULT_MODULE_LAYER_MODULE);
//
//        Assert.assertEquals(factories.size(), 9);
//        factories.forEach(System.out::println);
//    }
//
//    @Test
//    public void lookupByErrorCodeTest() {
//        ModuleExceptionBuilderFactory<?> moduleExceptionBuilderFactory = ModuleExceptionBuilderFactory.lookupByErrorCode("0000000001");
//
//        Assert.assertNotNull(moduleExceptionBuilderFactory);
//    }
//
//    @Test
//    public void registerTest() {
//        ModuleLayerModule testModule = new ModuleLayerModule(ModuleConstants.DEFAULT_APPLICATION_LAYER_MODULE, "testModule", "2");
//        ModuleExceptionBuilderFactory.register(TestException.class, testModule, "1", "测试内容模板", TEST_ALIAS_1, TEST_ALIAS_2, TEST_ALIAS_3);
//
//        ModuleExceptionBuilderFactory<TestException> err1 = ModuleExceptionBuilderFactory.lookupByAlias(TestException.class, TEST_ALIAS_1);
//        ModuleExceptionBuilderFactory<TestException> err2 = ModuleExceptionBuilderFactory.lookupByAlias(TestException.class, TEST_ALIAS_2);
//        ModuleExceptionBuilderFactory<TestException> err3 = ModuleExceptionBuilderFactory.lookupByAlias(TestException.class, TEST_ALIAS_3);
//        System.out.println(err1.instance().build().toString());
//
//        Assert.assertEquals(err1, err2);
//        Assert.assertEquals(err2, err3);
//    }
//
//    @Test
//    public void registerErrorTest() {
//        ModuleLayerModule testModule = new ModuleLayerModule(ModuleConstants.DEFAULT_APPLICATION_LAYER_MODULE, "testModule", "2");
//        ModuleExceptionBuilderFactory.register(TestException.class, testModule, "4", "测试内容模板", TEST_ALIAS_4);
//
//        boolean ef1 = false;
//        try {
//            ModuleExceptionBuilderFactory.register(TestException.class, testModule, "5", "测试内容模板", TEST_ALIAS_4);
//        } catch (IllegalArgumentException e) {
//            System.out.println(e);
//            ef1 = true;
//        }
//        Assert.assertTrue(ef1);
//
//        boolean ef2 = false;
//        try {
//            ModuleExceptionBuilderFactory.register(TestException.class, testModule, "4", "测试内容模板", TEST_ALIAS_5);
//        } catch (IllegalArgumentException e) {
//            System.out.println(e);
//            ef2 = true;
//        }
//        Assert.assertTrue(ef2);
//    }
//
//    static class TestException extends ApplicationException {
//
//        public static final String TEST_ALIAS_1 = "TEST_ALIAS_1";
//        public static final String TEST_ALIAS_2 = "TEST_ALIAS_2";
//        public static final String TEST_ALIAS_3 = "TEST_ALIAS_3";
//        public static final String TEST_ALIAS_4 = "TEST_ALIAS_4";
//        public static final String TEST_ALIAS_5 = "TEST_ALIAS_5";
//
//        public TestException(ModuleLayerModule moduleLayerModule, String detailCode, String message, Throwable cause) {
//            super(moduleLayerModule, detailCode, message, cause);
//        }
//
//    }

}
