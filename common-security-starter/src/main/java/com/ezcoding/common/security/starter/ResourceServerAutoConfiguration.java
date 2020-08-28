package com.ezcoding.common.security.starter;

import com.ezcoding.common.security.authority.CustomUserAuthenticationConverter;
import com.ezcoding.common.security.converter.StandardAccessTokenConverter;
import com.ezcoding.common.security.entrypoint.Oauth2AuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-29 11:42
 */
@Configuration
@EnableResourceServer
public class ResourceServerAutoConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private EzcodingSecurityConfigBean ezcodingSecurityConfigBean;
    @Autowired
    private HttpMessageConverters httpMessageConverters;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests().anyRequest().permitAll();
    }

    private String getVerifierKey() throws IOException {
        ClassPathResource resource = new ClassPathResource(ezcodingSecurityConfigBean.getPublicKey());
        byte[] bytes = new byte[resource.getInputStream().available()];
        resource.getInputStream().read(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws IOException {
        StandardAccessTokenConverter accessTokenConverter = new StandardAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setVerifier(new RsaVerifier(this.getVerifierKey()));
        jwtAccessTokenConverter.setAccessTokenConverter(accessTokenConverter);

        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(new JwtTokenStore(jwtAccessTokenConverter));

        DefaultOAuth2ExceptionRenderer defaultOAuth2ExceptionRenderer = new DefaultOAuth2ExceptionRenderer();
        defaultOAuth2ExceptionRenderer.setMessageConverters(httpMessageConverters.getConverters());
        Oauth2AuthenticationEntryPoint oauth2AuthenticationEntryPoint = new Oauth2AuthenticationEntryPoint();
        oauth2AuthenticationEntryPoint.setExceptionRenderer(defaultOAuth2ExceptionRenderer);

        resources
                .tokenServices(defaultTokenServices)
                .authenticationEntryPoint(oauth2AuthenticationEntryPoint);
    }

}
