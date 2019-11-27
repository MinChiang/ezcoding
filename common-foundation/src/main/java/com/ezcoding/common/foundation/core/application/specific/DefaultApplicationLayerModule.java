package com.ezcoding.common.foundation.core.application.specific;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-27 11:15
 */
public class DefaultApplicationLayerModule extends ApplicationLayerModule {

    private DefaultApplicationLayerModule() {
        super(DEFAULT_APPLICATION_NAME, String.valueOf(FILL_CHAR));
    }

    public static ApplicationLayerModule getInstance() {
        return DefaultApplicationLayerModuleHolder.INSTANCE;
    }

    private static class DefaultApplicationLayerModuleHolder {

        private static final ApplicationLayerModule INSTANCE = new DefaultApplicationLayerModule();

    }

}
