package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.IModuleNameable;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-28 23:19
 */
public class ModuleExceptionIdentification {

    /**
     * 详细错误码长度
     */
    public static final int DETAIL_CODE_LENGTH = 4;

    /**
     * 空缺内容填补字符
     */
    public static final char DEFAULT_FILL_CHAR = IModuleNameable.FILL_CHAR;

    /**
     * 错误所属模块
     */
    protected final ModuleLayerModule moduleLayerModule;

    /**
     * 错误详情码
     */
    protected final String detailCode;

    /**
     * 默认错误详情码长度
     */
    protected static int defaultCodeLength = DETAIL_CODE_LENGTH;

    /**
     * 默认填充字符
     */
    protected static char defaultFillChar = DEFAULT_FILL_CHAR;

    public ModuleExceptionIdentification(ModuleLayerModule moduleLayerModule, String detailCode) {
        if (moduleLayerModule == null || StringUtils.isAnyEmpty(detailCode)) {
            throw new IllegalArgumentException("应用模块、详细错误码不能为空");
        }
        if (detailCode.length() > defaultCodeLength) {
            throw new IllegalArgumentException("详细错误码长度必须小于等于" + defaultCodeLength);
        }
        this.moduleLayerModule = moduleLayerModule;
        this.detailCode = detailCode;
    }

    public ModuleLayerModule getModuleLayerModule() {
        return moduleLayerModule;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public static int getDefaultCodeLength() {
        return defaultCodeLength;
    }

    public static void setDefaultCodeLength(int defaultCodeLength) {
        ModuleExceptionIdentification.defaultCodeLength = defaultCodeLength;
    }

    public static char getDefaultFillChar() {
        return defaultFillChar;
    }

    public static void setDefaultFillChar(char defaultFillChar) {
        ModuleExceptionIdentification.defaultFillChar = defaultFillChar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModuleExceptionIdentification moduleExceptionIdentification = (ModuleExceptionIdentification) o;
        return moduleLayerModule.equals(moduleExceptionIdentification.moduleLayerModule) &&
                detailCode.equals(moduleExceptionIdentification.detailCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleLayerModule, detailCode);
    }

}
