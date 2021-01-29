package com.ezcoding.common.sdk.core;

import com.ezcoding.common.foundation.core.enviroment.Enviroment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-29 17:59
 */
public class SdkFactory {

    public static Map<Enviroment, SdkConfig> SDK_CONFIGS = new HashMap<>();

    static {
        SdkConfig local = new SdkConfig();
        local.setBaseUrl("http://127.0.0.1:8081");

        SdkConfig dev = new SdkConfig();
        dev.setBaseUrl("http://dev.ezcoding.com");

        SdkConfig test = new SdkConfig();
        dev.setBaseUrl("http://test.ezcoding.com");

        SdkConfig prod = new SdkConfig();
        dev.setBaseUrl("http://prod.ezcoding.com");

        SDK_CONFIGS.put(Enviroment.LOCAL, local);
        SDK_CONFIGS.put(Enviroment.DEV, dev);
        SDK_CONFIGS.put(Enviroment.TEST, test);
        SDK_CONFIGS.put(Enviroment.PROD, prod);
    }

    /**
     * 根据环境获取sdk实例
     *
     * @param enviroment 环境配置
     * @return sdk实例
     */
    public static Sdk create(Enviroment enviroment) {
        SdkConfig sdkConfig = SDK_CONFIGS.get(enviroment);
        if (sdkConfig == null) {
            throw new RuntimeException("can not find enviroment: [" + enviroment.toString() + "]");
        }
        return new Sdk(sdkConfig);
    }

}
