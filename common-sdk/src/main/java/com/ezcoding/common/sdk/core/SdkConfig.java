package com.ezcoding.common.sdk.core;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-29 18:23
 */
public class SdkConfig {

    public final String baseUrl;

    private SdkConfig(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static SdkConfigBuilder builder() {
        return new SdkConfigBuilder();
    }

    static class SdkConfigBuilder {

        private String baseUrl;

        private SdkConfigBuilder() {
        }

        public SdkConfig build() {
            return new SdkConfig(this.baseUrl);
        }

        public SdkConfigBuilder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

    }

}
