package com.ezcoding.common.foundation.core.exception;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-09 16:56
 */
public interface IMessageObjectTranslatable {

    /**
     * 翻译错误内容
     *
     * @param template 内容模板
     * @param params   参数内容
     * @return 翻译后的字符串
     */
    String translate(String template, List<Object> params);

}
