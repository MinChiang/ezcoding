package com.ezcoding.gateway.config;

import com.ezcoding.common.foundation.core.constant.GlobalConstants;
import com.ezcoding.common.foundation.core.context.ApplicationContextHolder;
import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * feign客户端自动设置对应用户信息
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-19 23:29
 */
@Configuration
@EnableFeignClients(basePackages = {"com.ezcoding.**.api"})
public class FeignConfig {

    @Autowired
    private AuthSettings authSettings;

    /**
     * 在发起远程请求前设置对应的用户请求头信息
     *
     * @return 请求拦截器
     */
    @Bean
    public RequestInterceptor tokenRequestInterceptor() {
        return template -> {
            Object token = ApplicationContextHolder.getInstance().get(GlobalConstants.Context.TOKEN);
            if (token != null) {
                template.header(authSettings.getHeader(), token.toString());
            }
        };
    }

    /**
     * feign日志级别
     *
     * @return 日志级别
     */
    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 解决@FeignClient与@RequestMapping同时存在而导致冲突问题
     *
     * @return spring扫描解析器
     */
    @Bean
    public WebMvcRegistrations feignWebRegistrations() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new RequestMappingHandlerMapping() {
                    @Override
                    protected boolean isHandler(Class<?> beanType) {
                        return super.isHandler(beanType) && !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class);
                    }
                };
            }
        };
    }

}
