package com.ezcoding.common.security.vote.voter;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.RequestMessageBuilder;
import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import com.ezcoding.common.security.metadatasource.DynamicAnnotationSecurityMetadataSource;
import com.ezcoding.common.web.util.ResponseUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-27 17:46
 */
public class RemoteDynamicRoleLoader implements IDynamicRoleLoadable {

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 发送地址
     */
    private String url;

    /**
     * 数据源
     */
    private DynamicAnnotationSecurityMetadataSource dynamicAnnotationSecurityMetadataSource;

    private RestTemplate restTemplate = new RestTemplate();

    public RemoteDynamicRoleLoader(String applicationName, String url, DynamicAnnotationSecurityMetadataSource dynamicAnnotationSecurityMetadataSource) {
        this.applicationName = applicationName;
        this.url = url;
        this.dynamicAnnotationSecurityMetadataSource = dynamicAnnotationSecurityMetadataSource;
    }

    @Override
    public Map<DynamicConfigAttribute, String> load() {
        Set<ConfigAttribute> attributes = dynamicAnnotationSecurityMetadataSource.acquireMetadata();
        RequestMessage<Set<ConfigAttribute>> requestMessage = RequestMessageBuilder.create(attributes).build();
        ResponseMessage<Map<DynamicConfigAttribute, String>> responseMessage = restTemplate.postForObject(this.url, requestMessage, ResponseMessage.class, this.applicationName);
        return ResponseUtils.checkAndGetResult(responseMessage);
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}