package com.ezcoding.common.foundation.core.tools.uuid;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 使用jdk原生UUID生成
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-27 9:30
 */
public class OriginalUuidProducer implements IdProduceable {

    private OriginalUuidProducer() {
    }

    public static OriginalUuidProducer getInstance() {
        return OriginalUUIDProducerHolder.INSTANCE;
    }

    @Override
    public String produce() {
        //此处不能调用replaceAll进行替换，replaceAll用于替换正则表达式
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }

    private static final class OriginalUUIDProducerHolder {

        private static OriginalUuidProducer INSTANCE = new OriginalUuidProducer();

    }

}
