package com.ezcoding.base.web.config;

import com.ezcoding.base.web.extend.spring.security.authority.CustomUserAuthenticationConverter;
import com.ezcoding.base.web.extend.spring.security.entryPoint.CustomOAuth2AuthenticationEntryPoint;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
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
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

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
        ClassPathResource resource = new ClassPathResource("key/rsa_public_key.pem");
        return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws IOException {
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setVerifier(new RsaVerifier(this.getVerifierKey()));
        jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);

        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(new JwtTokenStore(jwtAccessTokenConverter));

        resources
                .tokenServices(defaultTokenServices)
                .authenticationEntryPoint(new CustomOAuth2AuthenticationEntryPoint());
    }
}
