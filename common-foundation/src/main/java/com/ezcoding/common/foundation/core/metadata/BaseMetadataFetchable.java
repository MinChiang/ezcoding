package com.ezcoding.common.foundation.core.metadata;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-29 11:00
 */
public interface BaseMetadataFetchable extends MetadataFetchable {

    int PROPERTIES_FILE_FETCHER = 0;

    /**
     * 加载顺序，小序号的会覆盖大序号的
     *
     * @return 加载顺序
     */
    int order();

}
