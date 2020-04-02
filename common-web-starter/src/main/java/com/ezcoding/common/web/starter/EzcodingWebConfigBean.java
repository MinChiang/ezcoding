package com.ezcoding.common.web.starter;

import com.ezcoding.common.web.jwt.AuthSettings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:09
 */
@ConfigurationProperties("ezcoding.web")
public class EzcodingWebConfigBean {

    @NestedConfigurationProperty
    private AuthSettings authSettings = new AuthSettings();

    public AuthSettings getAuthSettings() {
        return authSettings;
    }

    public void setAuthSettings(AuthSettings authSettings) {
        this.authSettings = authSettings;
    }

}
