package com.ezcoding.common.foundation.core.metadata;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 16:24
 */
public class Metadata implements MetadataIdentifiable, Serializable {

    private static final long serialVersionUID = 5870215431745789395L;

    /**
     * 桶和key之间的连接字符串
     */
    private String bucketKeyJoiner;

    /**
     * 所在桶
     */
    private String bucket;

    /**
     * 对应key
     */
    private String key;

    /**
     * 序列化格式
     */
    private SerializationTypeEnum type;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 描述
     */
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metadata metadata = (Metadata) o;
        return bucket.equals(metadata.bucket) && key.equals(metadata.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucket, key);
    }

    public Metadata(String bucketKeyJoiner, String bucket, String key, SerializationTypeEnum type, String content, Date createTime, String description) {
        this.bucketKeyJoiner = bucketKeyJoiner;
        this.bucket = bucket;
        this.key = key;
        this.type = type;
        this.content = content;
        this.createTime = createTime;
        this.description = description;
    }

    public Metadata(String bucket, String key, SerializationTypeEnum type, String content) {
        this(null, bucket, key, type, content);
    }

    public Metadata(String bucketKeyJoiner, String bucket, String key, SerializationTypeEnum type, String content) {
        this(bucketKeyJoiner, bucket, key, type, content, null, null);
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SerializationTypeEnum getType() {
        return type;
    }

    public void setType(SerializationTypeEnum type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBucketKeyJoiner() {
        return bucketKeyJoiner;
    }

    public void setBucketKeyJoiner(String bucketKeyJoiner) {
        this.bucketKeyJoiner = bucketKeyJoiner;
    }

    @Override
    public String getIdentification() {
        if (this.bucketKeyJoiner == null) {
            return this.bucket + this.key;
        }
        return this.bucket + this.bucketKeyJoiner + this.key;
    }

}
