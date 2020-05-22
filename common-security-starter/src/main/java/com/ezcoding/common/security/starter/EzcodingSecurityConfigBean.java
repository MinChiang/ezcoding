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

    public static final String DYNAMIC_ROLE_LOAD_YAML = "role";

    @NestedConfigurationProperty
    private String privateKey = PRIVATE_KEY_PATH;

    @NestedConfigurationProperty
    private String publicKey = PUBLIC_KEY_PATH;

    private String defaultFailureUrl;

    private boolean enableDynamicRole = true;

    private boolean enableAutoLoader = false;

    private Long refreshSeconds = 3600L;

    private String dynamicRoleLoadYaml = DYNAMIC_ROLE_LOAD_YAML;

    private String dynamicRoleLoadUrl;

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

    public boolean isEnableAutoLoader() {
        return enableAutoLoader;
    }

    public void setEnableAutoLoader(boolean enableAutoLoader) {
        this.enableAutoLoader = enableAutoLoader;
    }

    public String getDynamicRoleLoadUrl() {
        return dynamicRoleLoadUrl;
    }

    public void setDynamicRoleLoadUrl(String dynamicRoleLoadUrl) {
        this.dynamicRoleLoadUrl = dynamicRoleLoadUrl;
    }

    public Long getRefreshSeconds() {
        return refreshSeconds;
    }

    public void setRefreshSeconds(Long refreshSeconds) {
        this.refreshSeconds = refreshSeconds;
    }

    public boolean isEnableDynamicRole() {
        return enableDynamicRole;
    }

    public void setEnableDynamicRole(boolean enableDynamicRole) {
        this.enableDynamicRole = enableDynamicRole;
    }

    public String getDynamicRoleLoadYaml() {
        return dynamicRoleLoadYaml;
    }

    public void setDynamicRoleLoadYaml(String dynamicRoleLoadYaml) {
        this.dynamicRoleLoadYaml = dynamicRoleLoadYaml;
    }

}
