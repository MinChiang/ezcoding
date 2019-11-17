package com.ezcoding.gateway.config;

import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.gateway.core.error.ApplicationFallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-29 9:42
 */
@Configuration
public class ZuulConfig {

    @Bean
    public ApplicationFallbackProvider applicationFallbackProvider() {
        return new ApplicationFallbackProvider(CommonApplicationExceptionConstants.COMMON_SERVICE_NOT_AVALIABLE.instance().build());
    }

}
