package com.ezcoding.module.user.config;

import com.ezcoding.common.foundation.util.ObjectMapperUtils;
import com.ezcoding.common.web.starter.EzcodingWebConfigBean;
import com.ezcoding.extend.user.KickOutHandler;
import com.ezcoding.module.user.service.IAuthorizationService;
import com.ezcoding.module.user.service.impl.AuthorizationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;

import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 11:33
 */
@Configuration
public class AuthenticationConfig {

    @Autowired
    private AuthorizationServerEndpointsConfiguration authorizationServerEndpointsConfiguration;

//    @Autowired
//    @Qualifier("serializableObjectMapper")
//    private ObjectMapper objectMapper;

    @Autowired
    private EzcodingWebConfigBean ezcodingWebConfigBean;

    @Bean
    public IAuthorizationService authorizationService() {
        AuthorizationImpl authorization = new AuthorizationImpl();
        authorization.setClientDetailsService(authorizationServerEndpointsConfiguration.getEndpointsConfigurer().getClientDetailsService());
        authorization.setOAuth2RequestFactory(authorizationServerEndpointsConfiguration.getEndpointsConfigurer().getOAuth2RequestFactory());
        authorization.setTokenGranter(authorizationServerEndpointsConfiguration.getEndpointsConfigurer().getTokenGranter());
        return authorization;
    }

    @Bean
    public RedisTemplate<String, Date> kickOutRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Date> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<Date> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Date.class);
        objectJackson2JsonRedisSerializer.setObjectMapper(ObjectMapperUtils.persist());
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public KickOutHandler kickOutHandler(RedisTemplate<String, Date> redisTemplate) {
        return new KickOutHandler(redisTemplate, ezcodingWebConfigBean.getAuthSettings().getExpiration());
    }

}
