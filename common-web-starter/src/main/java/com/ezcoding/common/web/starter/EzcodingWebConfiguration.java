package com.ezcoding.common.web.starter;

import com.ezcoding.common.foundation.starter.EzcodingFoundationAutoConfiguration;
import com.ezcoding.common.web.advice.WebApplicationAdviceConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-09 17:53
 */
@Configuration
@Import({WebCommonConfiguration.class, WebMvcConfiguration.class, WebApplicationAdviceConfig.class})
@EnableConfigurationProperties(EzcodingWebConfigBean.class)
@AutoConfigureAfter(EzcodingFoundationAutoConfiguration.class)
public class EzcodingWebConfiguration {

}
