package com.ezcoding.common.foundation.core.tools.uuid;

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
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static final class OriginalUUIDProducerHolder {

        private static final OriginalUuidProducer INSTANCE = new OriginalUuidProducer();

    }

}
