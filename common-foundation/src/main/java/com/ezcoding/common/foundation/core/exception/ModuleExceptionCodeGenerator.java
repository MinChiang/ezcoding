package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import org.apache.commons.lang3.StringUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-28 21:36
 */
public class ModuleExceptionCodeGenerator implements ExceptionCodeGeneratable {

    protected static int errorSuffixCodeLength = ERROR_SUFFIX_CODE_LENGTH;
    protected static char errorSuffixCodeFillChar = FILL_CHAR;

    /**
     * 功能模块
     */
    protected ModuleLayerModule moduleLayerModule;

    /**
     * 错误后缀码
     */
    protected String errorSuffixCode;

    public ModuleExceptionCodeGenerator(ModuleLayerModule moduleLayerModule, String errorSuffixCode) {
        if (StringUtils.isEmpty(errorSuffixCode)) {
            throw new IllegalArgumentException("错误后缀码不能为空");
        }
        if (errorSuffixCode.length() > errorSuffixCodeLength) {
            throw new IllegalArgumentException("错误后缀码长度必须小于等于" + errorSuffixCodeLength);
        }
        this.moduleLayerModule = moduleLayerModule;
        this.errorSuffixCode = StringUtils.leftPad(errorSuffixCode, errorSuffixCodeLength, errorSuffixCodeFillChar);
    }

    @Override
    public String generate() {
        return moduleLayerModule.getCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getErrorSuffixCode() {
        return errorSuffixCode;
    }

    public void setErrorSuffixCode(String errorSuffixCode) {
        this.errorSuffixCode = errorSuffixCode;
    }

    public static char getErrorSuffixCodeFillChar() {
        return errorSuffixCodeFillChar;
    }

    public static void setErrorSuffixCodeFillChar(char errorSuffixCodeFillChar) {
        ModuleExceptionCodeGenerator.errorSuffixCodeFillChar = errorSuffixCodeFillChar;
    }

    public static int getErrorSuffixCodeLength() {
        return errorSuffixCodeLength;
    }

    public static void setErrorSuffixCodeLength(int errorSuffixCodeLength) {
        ModuleExceptionCodeGenerator.errorSuffixCodeLength = errorSuffixCodeLength;
    }

}
