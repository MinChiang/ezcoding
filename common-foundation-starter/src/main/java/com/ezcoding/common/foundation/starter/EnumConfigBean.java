package com.ezcoding.common.foundation.starter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-07 10:34
 */
public class EnumConfigBean {

    public static final String SPRING_CONVERTER = "spring_converter";
    public static final String JACKSON = "jackson";
    public static final String DEFAULT_CONTEXTS = SPRING_CONVERTER + "," + JACKSON;

    private String strategies;
    private String packages;
    private String configContexts = DEFAULT_CONTEXTS;

    public String getStrategies() {
        return strategies;
    }

    public void setStrategies(String strategies) {
        this.strategies = strategies;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getConfigContexts() {
        return configContexts;
    }

    public void setConfigContexts(String configContexts) {
        this.configContexts = configContexts;
    }

}
