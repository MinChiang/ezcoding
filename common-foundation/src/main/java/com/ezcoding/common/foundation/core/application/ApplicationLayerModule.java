package com.ezcoding.common.foundation.core.application;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 应用（服务）级别模块
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:48
 */
public class ApplicationLayerModule implements IModuleNameable {

    protected final String applicationName;
    protected final String applicationCode;

    public ApplicationLayerModule(String applicationName, String applicationCode) {
        if (StringUtils.isAnyEmpty(applicationName, applicationCode)) {
            throw new IllegalArgumentException("系统名称，系统码不能为空");
        }
        if (applicationCode.length() > APPLICATION_CODE_LENGTH) {
            throw new IllegalArgumentException("系统码长度必须小于等于" + APPLICATION_CODE_LENGTH);
        }

        this.applicationName = applicationName;
        this.applicationCode = StringUtils.leftPad(applicationCode, APPLICATION_CODE_LENGTH, FILL_CHAR);
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
        return Objects.equals(applicationName, that.applicationName) &&
                Objects.equals(applicationCode, that.applicationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationName, applicationCode);
    }

}
