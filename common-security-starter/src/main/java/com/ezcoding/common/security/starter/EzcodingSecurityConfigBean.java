package com.ezcoding.common.security.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:09
 */
@ConfigurationProperties("ezcoding.security")
public class EzcodingSecurityConfigBean {

    public static final String PRIVATE_KEY_PATH = "key/rsa_private_key.pem";

    public static final String PUBLIC_KEY_PATH = "key/rsa_public_key.pem";

    @NestedConfigurationProperty
    private String privateKey = PRIVATE_KEY_PATH;

    @NestedConfigurationProperty
    private String publicKey = PUBLIC_KEY_PATH;

    private String defaultFailureUrl;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getDefaultFailureUrl() {
        return defaultFailureUrl;
    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }

}
