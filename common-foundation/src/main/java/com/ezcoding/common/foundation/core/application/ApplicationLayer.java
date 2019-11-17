package com.ezcoding.common.foundation.core.application;

import org.apache.commons.lang3.StringUtils;

/**
 * 应用（服务）级别模块
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:48
 */
public class ApplicationLayer implements IModuleNameable {

    private static int applicationCodeLength = 2;

    protected final String applicationName;
    protected final String applicationCode;

    public ApplicationLayer(String applicationName, String applicationCode) {
        this.applicationName = applicationName;
        this.applicationCode = applicationCode;
        this.validate();
    }

    /**
     * 验证输入参数
     */
    private void validate() {
        if (StringUtils.isAnyEmpty(applicationName, applicationCode)) {
            throw new IllegalArgumentException("系统名称，系统码不能为空");
        }
        if (applicationCode.length() != applicationCodeLength) {
            throw new IllegalArgumentException("系统码长度必须为" + applicationCodeLength);
        }
    }

    @Override
    public String getPath(String pathSplitter) {
        return applicationName;
    }

    @Override
    public String getCode() {
        return applicationCode;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public static int getApplicationCodeLength() {
        return applicationCodeLength;
    }

    public static void setApplicationCodeLength(int applicationCodeLength) {
        ApplicationLayer.applicationCodeLength = applicationCodeLength;
    }

}
