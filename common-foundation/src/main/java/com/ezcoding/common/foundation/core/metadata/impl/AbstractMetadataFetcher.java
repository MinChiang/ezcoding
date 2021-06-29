package com.ezcoding.common.foundation.core.metadata.impl;

import com.ezcoding.common.foundation.core.metadata.BaseMetadataFetchable;
import com.ezcoding.common.foundation.core.metadata.MetadataIdentifiable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-29 10:53
 */
public abstract class AbstractMetadataFetcher implements BaseMetadataFetchable {

    private final int order;
    private final String bucketKeyJoiner;

    public AbstractMetadataFetcher(int order, String bucketKeyJoiner) {
        this.order = order;
        if (bucketKeyJoiner == null || bucketKeyJoiner.isEmpty()) {
            this.bucketKeyJoiner = MetadataIdentifiable.BUCKET_KEY_JOINER;
        } else {
            this.bucketKeyJoiner = bucketKeyJoiner;
        }
    }

    @Override
    public int order() {
        return this.order;
    }

    @Override
    public String getBucketKeyJoiner() {
        return this.bucketKeyJoiner;
    }

}
