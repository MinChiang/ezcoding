package com.ezcoding.starter.foundation.config;

import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:09
 */
@Data
@ConfigurationProperties("ezcoding.foundation")
public class EzcodingFoundationConfigBean {

    @NestedConfigurationProperty
    private MessageConfigBean message = new MessageConfigBean();

    @NestedConfigurationProperty
    private AuthSettings auth = new AuthSettings();

    @NestedConfigurationProperty
    private MetadataConfigBean metadata = new MetadataConfigBean();

}
