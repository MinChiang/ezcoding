package com.ezcoding.common.sdk.core;

import com.ezcoding.common.foundation.core.enviroment.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-29 17:59
 */
public class SdkFactory {

    public static Map<Environment, SdkConfig> SDK_CONFIGS = new HashMap<Environment, SdkConfig>() {{
        SdkConfig local = new SdkConfig();
        local.setBaseUrl("http://127.0.0.1:8081");

        SdkConfig dev = new SdkConfig();
        dev.setBaseUrl("http://dev.ezcoding.com");

        SdkConfig test = new SdkConfig();
        dev.setBaseUrl("http://test.ezcoding.com");

        SdkConfig prod = new SdkConfig();
        dev.setBaseUrl("http://prod.ezcoding.com");

        SDK_CONFIGS.put(Environment.LOCAL, local);
        SDK_CONFIGS.put(Environment.DEV, dev);
        SDK_CONFIGS.put(Environment.TEST, test);
        SDK_CONFIGS.put(Environment.PROD, prod);
    }};

    /**
     * 根据环境获取sdk实例
     *
     * @param environment 环境配置
     * @return sdk实例
     */
    public static Sdk create(Environment environment) {
        Objects.requireNonNull(environment);
        SdkConfig sdkConfig = SDK_CONFIGS.get(environment);
        if (sdkConfig == null) {
            throw new RuntimeException("can not find environment: [" + environment.toString() + "]");
        }
        return new Sdk(sdkConfig);
    }

}
