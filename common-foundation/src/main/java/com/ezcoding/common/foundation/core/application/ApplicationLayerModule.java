package com.ezcoding.common.foundation.core.application;

import java.util.Objects;

/**
 * 应用（服务）级别模块
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:48
 */
public class ApplicationLayerModule implements ModuleNameable {

    /**
     * 应用名称
     */
    protected final String applicationName;

    /**
     * 应用号
     */
    protected final String applicationCode;

    ApplicationLayerModule(String applicationName, String applicationCode) {
        this.applicationName = applicationName;
        this.applicationCode = applicationCode;
    }

    ApplicationLayerModule(String applicationName, int applicationCode) {
        this(applicationName, String.valueOf(applicationCode));
    }

    @Override
    public String getPath(String pathSplitter) {
        return applicationName;
    }

    @Override
    public String getCode() {
        return applicationCode;
    }

    @Override
    public String getName() {
        return applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplicationLayerModule that = (ApplicationLayerModule) o;
        return applicationCode.equals(that.applicationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationCode);
    }

    @Override
    public String toString() {
        return "ApplicationLayerModule{" +
                "applicationName='" + applicationName + '\'' +
                ", applicationCode='" + applicationCode + '\'' +
                '}';
    }

}
