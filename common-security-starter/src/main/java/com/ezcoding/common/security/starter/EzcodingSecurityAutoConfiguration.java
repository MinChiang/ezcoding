package com.ezcoding.common.security.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-09 14:20
 */
@Configuration
@Import({MethodSecurityAutoConfiguration.class, ResourceServerAutoConfiguration.class, SecurityCommonConfiguration.class})
@EnableConfigurationProperties(EzcodingSecurityConfigBean.class)
@ConditionalOnProperty(prefix = "ezcoding.security", name = "enabled", havingValue = "true", matchIfMissing = true)
public class EzcodingSecurityAutoConfiguration {

}
