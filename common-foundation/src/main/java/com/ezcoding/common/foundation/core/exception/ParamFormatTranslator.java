package com.ezcoding.common.foundation.core.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-18 15:39
 */
public class ParamFormatTranslator implements IMessageObjectTranslatable {

    private static final String PREFIX = "{";
    private static final String SUFFIX = "}";

    @Override
    public String translate(String code, List<Object> params) {
        if (code == null || CollectionUtils.isEmpty(params)) {
            return code;
        }

        String temp = code;
        for (int i = 0; i < params.size(); i++) {
            temp = StringUtils.replace(temp, PREFIX + i + SUFFIX, params.get(i).toString());
        }
        return temp;
    }

}
