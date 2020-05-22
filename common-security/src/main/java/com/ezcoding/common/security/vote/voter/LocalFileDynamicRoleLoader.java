package com.ezcoding.common.security.vote.voter;

import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-21 14:10
 */
public class LocalFileDynamicRoleLoader implements DynamicRoleLoadable {

    private static final String FILE_NAME_SUFFIX = ".yaml";

    /**
     * 加载的配置文件
     */
    private String fileName;

    /**
     * 系统号
     */
    private String applicationName;

    public LocalFileDynamicRoleLoader(String fileName, String applicationName) {
        this.fileName = fileName;
        this.applicationName = applicationName;
    }

    @Override
    public Map<DynamicConfigAttribute, String> load() {
        YamlMapFactoryBean yaml = new YamlMapFactoryBean();
        yaml.setResources(new ClassPathResource(this.fileName + FILE_NAME_SUFFIX));
        Map<String, Object> object = yaml.getObject();

        Map<DynamicConfigAttribute, String> dynamicConfigAttributes = new HashMap<>();
        if (object == null) {
            return dynamicConfigAttributes;
        }

        for (Map.Entry<String, Object> classes : object.entrySet()) {
            String className = classes.getKey();
            for (Map.Entry<String, Object> methods : ((Map<String, Object>) classes.getValue()).entrySet()) {
                DynamicConfigAttribute dynamicConfigAttribute = new DynamicConfigAttribute(this.applicationName, className, methods.getKey());
                dynamicConfigAttributes.put(dynamicConfigAttribute, (String) methods.getValue());
            }
        }

        return dynamicConfigAttributes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

}
