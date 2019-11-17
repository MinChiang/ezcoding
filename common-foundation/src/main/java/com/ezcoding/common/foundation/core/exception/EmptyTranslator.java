package com.ezcoding.common.foundation.core.exception;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-09 17:17
 */
public class EmptyTranslator implements IMessageObjectTranslatable {

    @Override
    public String translate(String code, List<Object> params) {
        return code;
    }

}
