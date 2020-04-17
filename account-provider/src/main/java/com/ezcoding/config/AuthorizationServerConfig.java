package com.ezcoding.config;

import com.ezcoding.common.constant.UserModuleConstants;
import com.ezcoding.common.foundation.util.ObjectMapperUtils;
import com.ezcoding.common.security.accessTokenConverter.StandardAccessTokenConverter;
import com.ezcoding.common.security.authority.CustomUserAuthenticationConverter;
import com.ezcoding.common.security.entrypoint.Oauth2AuthenticationEntryPoint;
import com.ezcoding.common.security.starter.EzcodingSecurityConfigBean;
import com.ezcoding.extend.spring.security.code.RedisAuthorizationCodeServices;
import com.ezcoding.extend.spring.security.detailservice.ClientDetailsServiceImpl;
import com.ezcoding.extend.spring.security.tokenService.CustomTokenServicce;
import com.ezcoding.module.user.dao.ClientDetailsMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-25 0:36
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig implements AuthorizationServerConfigurer {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientDetailsMapper clientDetailsMapper;
    @Autowired
    private RedisConnectionFactory factory;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private EzcodingSecurityConfigBean ezcodingSecurityConfigBean;
    @Autowired
    private HttpMessageConverters httpMessageConverters;

    private RedisTemplate<String, ClientDetails> clientDetailsRedisTemplate() {
        RedisTemplate<String, ClientDetails> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<ClientDetails> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(ClientDetails.class);
        objectJackson2JsonRedisSerializer.setObjectMapper(ObjectMapperUtils.persist());
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    private RedisTemplate<String, OAuth2Authentication> oAuth2AuthenticationRedisTemplate() {
        RedisTemplate<String, OAuth2Authentication> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<OAuth2Authentication> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(OAuth2Authentication.class);
        objectJackson2JsonRedisSerializer.setObjectMapper(ObjectMapperUtils.persist());
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    private ClientDetailsService clientDetailsService() {
        ClientDetailsServiceImpl clientDetailsServiceImpl = new ClientDetailsServiceImpl();
        clientDetailsServiceImpl.setClientDetailsMapper(clientDetailsMapper);
        clientDetailsServiceImpl.setRedisTemplate(clientDetailsRedisTemplate());
        clientDetailsServiceImpl.setPrefix(UserModuleConstants.ACCOUNT_USER_MODEUL_NAME + ":client_details:");
        clientDetailsServiceImpl.setUseCache(true);
        return clientDetailsServiceImpl;
    }

    private RedisAuthorizationCodeServices redisAuthorizationCodeServices() {
        RedisAuthorizationCodeServices redisAuthorizationCodeServices = new RedisAuthorizationCodeServices();
        redisAuthorizationCodeServices.setRedisTemplate(oAuth2AuthenticationRedisTemplate());
        redisAuthorizationCodeServices.setPrefix(UserModuleConstants.ACCOUNT_USER_MODEUL_NAME + ":authorization_code:");
        return redisAuthorizationCodeServices;
    }

    private String getSigningKey() throws IOException {
        ClassPathResource resource = new ClassPathResource(ezcodingSecurityConfigBean.getPrivateKey());
        return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    private String getVerifierKey() throws IOException {
        ClassPathResource resource = new ClassPathResource(ezcodingSecurityConfigBean.getPublicKey());
        return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    private AuthorizationServerTokenServices customTokenServices(ClientDetailsService clientDetailsService,
                                                                 UserDetailsService userDetailsService,
                                                                 TokenEnhancer tokenEnhancer,
                                                                 TokenStore tokenStore) {
        CustomTokenServicce customTokenServicce = new CustomTokenServicce();
        customTokenServicce.setTokenStore(tokenStore);
        customTokenServicce.setSupportRefreshToken(true);
        customTokenServicce.setReuseRefreshToken(true);
        customTokenServicce.setClientDetailsService(clientDetailsService);
        customTokenServicce.setTokenEnhancer(tokenEnhancer);

        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
        customTokenServicce.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));

        return customTokenServicce;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        DefaultOAuth2ExceptionRenderer defaultOAuth2ExceptionRenderer = new DefaultOAuth2ExceptionRenderer();
        defaultOAuth2ExceptionRenderer.setMessageConverters(httpMessageConverters.getConverters());
        Oauth2AuthenticationEntryPoint oauth2AuthenticationEntryPoint = new Oauth2AuthenticationEntryPoint();
        oauth2AuthenticationEntryPoint.setExceptionRenderer(defaultOAuth2ExceptionRenderer);

        security
                //关闭/oauth/token_key验证端点权限访问
                .tokenKeyAccess("permitAll()")
                //开启/oauth/check_token验证端点认证访问权限
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder)
                .authenticationEntryPoint(oauth2AuthenticationEntryPoint);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .withClientDetails(clientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws IOException {
        StandardAccessTokenConverter standardAccessTokenConverter = new StandardAccessTokenConverter();
        standardAccessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(this.getSigningKey());
        jwtAccessTokenConverter.setVerifier(new RsaVerifier(this.getVerifierKey()));
        jwtAccessTokenConverter.setAccessTokenConverter(standardAccessTokenConverter);

        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter);

        AuthorizationServerTokenServices authorizationServerTokenServices = customTokenServices(
                clientDetailsService(),
                userDetailsService,
                jwtAccessTokenConverter,
                jwtTokenStore
        );

        endpoints
                .tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .authorizationCodeServices(redisAuthorizationCodeServices())
                .userDetailsService(userDetailsService)
                .tokenServices(authorizationServerTokenServices);
    }

}
