package com.ezcoding.gateway.config;

import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-20 10:30
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer(AuthSettings authSettings) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //允许跨域标识
                registry
                        .addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*")
                        //当进行跨域请求时，前端APP只能获取默认的几个header，此时需要设置Access-Control-Expose-Headers的值
                        .exposedHeaders(authSettings.getHeader())
                        .allowCredentials(true);
            }
        };
    }

}
