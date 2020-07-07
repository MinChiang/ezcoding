package com.ezcoding.common.foundation.core.exception;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-28 21:33
 */
public interface ExceptionCodeGeneratable {

    /**
     * 错误后缀长度
     */
    int ERROR_SUFFIX_CODE_LENGTH = 4;

    /**
     * 空缺内容填补字符
     */
    char FILL_CHAR = '0';

    /**
     * 产生错误唯一标识
     *
     * @return 错误唯一标识
     */
    String generate();

}
