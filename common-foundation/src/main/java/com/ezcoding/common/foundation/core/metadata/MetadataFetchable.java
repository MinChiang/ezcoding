package com.ezcoding.common.foundation.core.metadata;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 16:39
 */
public interface MetadataFetchable {

    String SPLITTER = ".";
    String SPLITTER_REG = "\\.";
    String FILE_SUFFIX = ".properties";

    String KEY_TYPE = "type";
    String CONTENT = "content";
    String CREATE_TIME = "createTime";
    String DESCRIPTION = "description";

    /**
     * 获取桶中的所有元数据字典
     *
     * @return 所有元数据字典
     */
    Map<String, Metadata> fetch();

    /**
     * 获取桶和key的连接符
     *
     * @return 连接符
     */
    String getBucketKeyJoiner();

}
