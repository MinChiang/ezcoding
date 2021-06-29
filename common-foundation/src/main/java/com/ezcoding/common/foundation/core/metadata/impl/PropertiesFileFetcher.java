package com.ezcoding.common.foundation.core.metadata.impl;

import com.ezcoding.common.foundation.core.metadata.BaseMetadataFetchable;
import com.ezcoding.common.foundation.core.metadata.Metadata;
import com.ezcoding.common.foundation.core.metadata.MetadataIdentifiable;
import com.ezcoding.common.foundation.core.metadata.SerializationTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 16:47
 */
public class PropertiesFileFetcher extends AbstractMetadataFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesFileFetcher.class);

    private final List<File> files;

    public PropertiesFileFetcher(int order, String bucketkeyjoiner, List<File> files) {
        super(order, bucketkeyjoiner);
        if (files == null || files.isEmpty() || files.stream().filter(File::isFile).filter(File::canRead).count() != files.size()) {
            throw new IllegalArgumentException("files must not empty and can be read!");
        }
        this.files = files;
    }

    public PropertiesFileFetcher(List<File> files) {
        this(BaseMetadataFetchable.PROPERTIES_FILE_FETCHER, MetadataIdentifiable.BUCKET_KEY_JOINER, files);
    }

    @Override
    public Map<String, Metadata> fetch() {
        Map<String, Metadata> completeMetadata = new HashMap<>();
        try {
            for (File file : files) {
                String fileName = file.getName();
                if (!fileName.endsWith(FILE_SUFFIX)) {
                    continue;
                }
                String bucket = fileName.substring(0, fileName.lastIndexOf(FILE_SUFFIX));

                Properties properties = new Properties();
                FileInputStream fis = new FileInputStream(file);
                properties.load(fis);
                fis.close();

                Map<String, TemporaryMetadata> incompleteMetadata = new HashMap<>();

                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    TemporaryMetadata temporaryMetadata = new TemporaryMetadata();
                    temporaryMetadata.setBucketkeyjoiner(getBucketKeyJoiner());
                    if (isSimpleFormat(key)) {
                        temporaryMetadata.setBucket(bucket);
                        temporaryMetadata.setKey(key);
                        temporaryMetadata.setType(SerializationTypeEnum.STRING);
                        temporaryMetadata.setContent(value);
                        incompleteMetadata.put(key, temporaryMetadata);
                    } else {
                        String[] arr = key.split(SPLITTER_REG);
                        if (arr.length != 2) {
                            LOGGER.warn("properties key must like [key].[type|content|createTime|description]");
                            continue;
                        }
                        temporaryMetadata = incompleteMetadata.computeIfAbsent(arr[0], k -> new TemporaryMetadata());
                        temporaryMetadata.setBucket(bucket);
                        temporaryMetadata.setKey(arr[0]);
                        switch (arr[1]) {
                            case KEY_TYPE:
                                SerializationTypeEnum from = SerializationTypeEnum.from(value);
                                if (from == null) {
                                    LOGGER.warn("properties : {} type must in [string, json]", key);
                                    continue;
                                }
                                temporaryMetadata.setType(from);
                                break;
                            case CONTENT:
                                temporaryMetadata.setContent(value);
                                break;
                            case CREATE_TIME:
                                try {
                                    long timestamp = Long.parseLong(value);
                                    temporaryMetadata.setCreateTime(new Date(timestamp));
                                } catch (Exception e) {
                                    LOGGER.warn("properties : {} parse timestamp error", key);
                                    continue;
                                }
                                break;
                            case DESCRIPTION:
                                temporaryMetadata.setDescription(value);
                                break;
                            default:
                                LOGGER.warn("properties : {} key must like [key].[type|content|createTime|description]", key);
                        }
                    }
                }

                for (TemporaryMetadata temporaryMetadata : incompleteMetadata.values()) {
                    Metadata metadata = temporaryMetadata.convertToMetadata();
                    if (metadata == null) {
                        LOGGER.warn("metadata : {} is incomplete, missing[key|content]", temporaryMetadata);
                        continue;
                    }
                    Metadata pre = completeMetadata.put(metadata.getIdentification(), metadata);
                    if (pre != null) {
                        LOGGER.warn("metadata : {} is convered!", pre);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return completeMetadata;
    }

    /**
     * 是否是简单格式
     *
     * @param key 待判断的key
     * @return 是否是简单格式
     */
    private boolean isSimpleFormat(String key) {
        return !key.contains(SPLITTER);
    }

    private static class TemporaryMetadata {

        private String bucketkeyjoiner;
        private String bucket;
        private String key;
        private SerializationTypeEnum type;
        private String content;
        private Date createTime;
        private String description;

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

        public String getBucketkeyjoiner() {
            return bucketkeyjoiner;
        }

        public void setBucketkeyjoiner(String bucketkeyjoiner) {
            this.bucketkeyjoiner = bucketkeyjoiner;
        }

        public Metadata convertToMetadata() {
            if (this.bucket == null || this.key == null || this.content == null) {
                return null;
            }
            return new Metadata(this.bucketkeyjoiner, this.bucket, this.key, this.type, this.content, this.createTime, this.description);
        }

        @Override
        public String toString() {
            return "TemporaryMetadata{" +
                    "bucketkeyjoiner='" + bucketkeyjoiner + '\'' +
                    ", bucket='" + bucket + '\'' +
                    ", key='" + key + '\'' +
                    ", type=" + type +
                    ", content='" + content + '\'' +
                    ", createTime=" + createTime +
                    ", description='" + description + '\'' +
                    '}';
        }

    }

}
