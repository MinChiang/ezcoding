package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.IModuleNameable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:46
 */
public interface IApplicationException {

    /**
     * 详细错误码长度
     */
    int DETAIL_CODE_LENGTH = 4;

    /**
     * 空缺内容填补字符
     */
    char FILL_CHAR = IModuleNameable.FILL_CHAR;

    /**
     * 获取完整的错误码
     *
     * @return 完整的错误码
     */
    String getCode();

}
