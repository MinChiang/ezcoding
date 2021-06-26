package com.ezcoding.common.sdk.core;

import com.ezcoding.common.foundation.core.enviroment.EnvironmentEnum;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-29 17:59
 */
public class SdkFactory {

    private static final String PROPERTIES_SUFFIX = ".properties";

    private static final String BASE_URL = "baseUrl";

    private SdkFactory() {
    }

    public static SdkFactory getInstance() {
        return SdkFactoryHolder.HOLDER;
    }

    /**
     * 根据环境获取sdk实例
     *
     * @param environmentEnum 环境配置
     * @return sdk实例
     */
    public SdkImpl create(EnvironmentEnum environmentEnum) {
        Objects.requireNonNull(environmentEnum);
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(environmentEnum.id + PROPERTIES_SUFFIX));
            SdkConfig sdkConfig = SdkConfig.builder()
                    .setBaseUrl(properties.getProperty(BASE_URL))
                    .build();
            return new SdkImpl(sdkConfig);
        } catch (IOException e) {
            throw new RuntimeException("properties read error!");
        }
    }

    private static class SdkFactoryHolder {

        private static final SdkFactory HOLDER = new SdkFactory();

    }

}
