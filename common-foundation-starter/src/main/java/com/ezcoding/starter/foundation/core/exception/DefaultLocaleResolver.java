package com.ezcoding.starter.foundation.core.exception;

import java.util.Locale;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-29 9:19
 */
public class DefaultLocaleResolver implements LocaleResolvable {

    @Override
    public Locale resolve() {
        return Locale.getDefault();
    }

}
