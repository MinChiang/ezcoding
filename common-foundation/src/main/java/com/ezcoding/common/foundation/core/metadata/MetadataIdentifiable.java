package com.ezcoding.common.foundation.core.metadata;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 16:32
 */
public interface MetadataIdentifiable {

    String BUCKET_KEY_JOINER = ":";

    /**
     * 获取id
     *
     * @return id
     */
    String getIdentification();

}
