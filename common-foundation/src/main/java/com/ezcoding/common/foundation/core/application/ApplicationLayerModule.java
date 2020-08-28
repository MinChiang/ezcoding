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

    protected static int applicationCodeLength = APPLICATION_CODE_LENGTH;
    protected static char applicationFillChar = FILL_CHAR;

    protected final String applicationName;
    protected final String applicationCode;

    public ApplicationLayerModule(String applicationName, String applicationCode) {
        if (applicationName == null || applicationName.isEmpty() || applicationCode == null || applicationCode.isEmpty()) {
            throw new IllegalArgumentException("系统名称，系统码不能为空");
        }
        if (applicationCode.length() > applicationCodeLength) {
            throw new IllegalArgumentException("系统码长度必须小于等于" + applicationCodeLength);
        }

        this.applicationName = applicationName;
        this.applicationCode = ModuleNameable.leftPad(applicationCode, applicationCodeLength, applicationFillChar);
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

    public static int getApplicationCodeLength() {
        return applicationCodeLength;
    }

    public static void setApplicationCodeLength(int applicationCodeLength) {
        ApplicationLayerModule.applicationCodeLength = applicationCodeLength;
    }

    public static char getApplicationFillChar() {
        return applicationFillChar;
    }

    public static void setApplicationFillChar(char applicationFillChar) {
        ApplicationLayerModule.applicationFillChar = applicationFillChar;
    }

    @Override
    public String toString() {
        return "ApplicationLayerModule{" +
                "applicationName='" + applicationName + '\'' +
                ", applicationCode='" + applicationCode + '\'' +
                '}';
    }

}
