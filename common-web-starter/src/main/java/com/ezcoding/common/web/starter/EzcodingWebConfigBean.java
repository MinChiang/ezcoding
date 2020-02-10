package com.ezcoding.common.web.starter;

import com.ezcoding.common.web.jwt.AuthSettings;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:09
 */
@Data
@ConfigurationProperties("ezcoding.web")
public class EzcodingWebConfigBean {

    @NestedConfigurationProperty
    private AuthSettings authSettings = new AuthSettings();

}
