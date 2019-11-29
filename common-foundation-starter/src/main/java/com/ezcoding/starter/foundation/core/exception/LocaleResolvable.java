package com.ezcoding.starter.foundation.core.exception;

import java.util.Locale;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-29 9:18
 */
public interface LocaleResolvable {

    /**
     * 获取语言
     *
     * @return 获取到的当前语言
     */
    Locale resolve();

}
