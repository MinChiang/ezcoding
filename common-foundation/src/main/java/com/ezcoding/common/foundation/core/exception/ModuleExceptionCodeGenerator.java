package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleNameable;

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

    /**
     * 完整的错误码
     */
    private final String fullCode;

    public ModuleExceptionCodeGenerator(ModuleLayerModule moduleLayerModule, String errorSuffixCode) {
        if (errorSuffixCode == null || errorSuffixCode.isEmpty()) {
            throw new IllegalArgumentException("error suffix code can not be null");
        }
        if (errorSuffixCode.length() > errorSuffixCodeLength) {
            throw new IllegalArgumentException("error suffix code length must less than " + errorSuffixCodeLength);
        }
        this.moduleLayerModule = moduleLayerModule;
        this.errorSuffixCode = ModuleNameable.leftPad(errorSuffixCode, errorSuffixCodeLength, errorSuffixCodeFillChar);
        this.fullCode = moduleLayerModule.getCode() + this.errorSuffixCode;
    }

    @Override
    public String generate() {
        return this.fullCode;
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
