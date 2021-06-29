package com.ezcoding.common.foundation.core.metadata.impl;

import com.ezcoding.common.foundation.core.metadata.BaseMetadataFetchable;
import com.ezcoding.common.foundation.core.metadata.Metadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-29 19:38
 */
public class SimpleMetadataFetcher extends AbstractMetadataFetcher {

    private Map<String, Metadata> map;

    public SimpleMetadataFetcher(int order, String bucketKeyJoiner, Map<String, Metadata> map) {
        super(order, bucketKeyJoiner);
        if (map == null || map.isEmpty()) {
            return;
        }
        this.map = Collections.unmodifiableMap(new HashMap<>(map));
    }

    public SimpleMetadataFetcher(Map<String, Metadata> map) {
        this(BaseMetadataFetchable.SIMPLE_METADATA_FETCHER, null, map);
    }

    @Override
    public Map<String, Metadata> fetch() {
        return this.map;
    }

}
