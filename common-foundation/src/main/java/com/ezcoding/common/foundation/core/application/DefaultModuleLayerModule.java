package com.ezcoding.common.foundation.core.application;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-27 11:09
 */
public class DefaultModuleLayerModule extends ModuleLayerModule {

    private DefaultModuleLayerModule() {
        super(DefaultApplicationLayerModule.getInstance(), DEFAULT_MODULE_NAME, IModuleNameable.fillBlankChar(String.valueOf(FILL_CHAR)));
    }

    public static ModuleLayerModule getInstance() {
        return DefaultModuleLayerModuleHolder.INSTANCE;
    }

    private static class DefaultModuleLayerModuleHolder {

        private static final ModuleLayerModule INSTANCE = new DefaultModuleLayerModule();

    }

}
