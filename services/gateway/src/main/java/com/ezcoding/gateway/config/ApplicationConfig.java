package com.ezcoding.gateway.config;

import com.ezcoding.common.foundation.core.application.ApplicationMetadata;
import com.ezcoding.common.foundation.core.exception.ExceptionBuilder;
import com.ezcoding.common.foundation.core.exception.ParamFormatTranslator;
import com.ezcoding.common.foundation.util.ApplicationUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-26 22:38
 */
@Configuration
public class ApplicationConfig implements InitializingBean {

    @Value("${metadata.dataCenterNo}")
    private Integer dataCenterNo;
    @Value("${metadata.category}")
    private String category;
    @Value("${metadata.categoryNo}")
    private Integer categoryNo;

    @Bean
    public ApplicationMetadata applicationMetadata() {
        return new ApplicationMetadata(dataCenterNo, category, categoryNo);
    }

    @Override
    public void afterPropertiesSet() {
        ApplicationUtils.setApplicationMetadata(applicationMetadata());
        ExceptionBuilder.configTranslator(new ParamFormatTranslator());
    }

}
