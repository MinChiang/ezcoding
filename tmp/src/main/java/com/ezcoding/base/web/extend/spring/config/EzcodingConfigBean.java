package com.ezcoding.base.web.extend.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:09
 */
@Data
@ConfigurationProperties("ezcoding")
public class EzcodingConfigBean {

    @NestedConfigurationProperty
    private MessageConfigBean message = new MessageConfigBean();

    @NestedConfigurationProperty
    private AuthConfigBean auth = new AuthConfigBean();

    @NestedConfigurationProperty
    private MetadataConfigBean metadata;

}
