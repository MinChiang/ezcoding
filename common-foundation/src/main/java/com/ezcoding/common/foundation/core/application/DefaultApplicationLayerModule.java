package com.ezcoding.common.foundation.core.application;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-27 11:15
 */
public class DefaultApplicationLayerModule extends ApplicationLayerModule {

    private DefaultApplicationLayerModule() {
        super(DEFAULT_APPLICATION_NAME, IModuleNameable.fillBlankChar(String.valueOf(FILL_CHAR)));
    }

    public static ApplicationLayerModule getInstance() {
        return DefaultApplicationLayerModuleHolder.INSTANCE;
    }

    private static class DefaultApplicationLayerModuleHolder {

        private static final ApplicationLayerModule INSTANCE = new DefaultApplicationLayerModule();

    }

}
