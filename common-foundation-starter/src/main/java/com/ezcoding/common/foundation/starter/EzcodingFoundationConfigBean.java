package com.ezcoding.common.foundation.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:09
 */
@ConfigurationProperties("ezcoding.foundation")
public class EzcodingFoundationConfigBean {

    @NestedConfigurationProperty
    private MessageConfigBean message = new MessageConfigBean();

    @NestedConfigurationProperty
    private MetadataConfigBean metadata = new MetadataConfigBean();

    public MessageConfigBean getMessage() {
        return message;
    }

    public void setMessage(MessageConfigBean message) {
        this.message = message;
    }

    public MetadataConfigBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataConfigBean metadata) {
        this.metadata = metadata;
    }

}
