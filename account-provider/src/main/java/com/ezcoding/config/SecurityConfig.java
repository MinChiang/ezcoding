package com.ezcoding.config;

import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;
import com.ezcoding.common.security.starter.EzcodingSecurityConfigBean;
import com.ezcoding.extend.spring.security.failureHandler.CustomAuthenticationFailureHandler;
import com.ezcoding.extend.spring.security.filter.MultipleAuthenticationFilter;
import com.ezcoding.extend.spring.security.provider.*;
import com.ezcoding.extend.spring.security.successHandler.LoginRecordSuccessHandler;
import com.ezcoding.extend.spring.security.successHandler.SuccessHandlerChain;
import com.ezcoding.module.user.core.authentication.*;
import com.ezcoding.module.user.core.verification.RedisVerificationServiceImpl;
import com.ezcoding.module.user.dao.LoginInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-25 0:36
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private IBasicUserService basicUserService;
    @Resource(name = "imageVerificationService")
    private RedisVerificationServiceImpl imageVerificationService;
    @Resource(name = "numberVerificationService")
    private RedisVerificationServiceImpl numberVerificationService;
    @Autowired
    private ICustomUserDetailsService customUserDetailsService;
    @Autowired
    private EzcodingSecurityConfigBean ezcodingSecurityConfigBean;

    private MultipleAuthenticationFilter multipleAuthenticationFilter() throws Exception {
        SuccessHandlerChain successHandlerChain = new SuccessHandlerChain();
        successHandlerChain.addAuthenticationSuccessHandler(new LoginRecordSuccessHandler(loginInfoMapper));
        successHandlerChain.addAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler());

        MultipleAuthenticationFilter multipleAuthenticationFilter = new MultipleAuthenticationFilter("/oauth/authorize");
        Optional.ofNullable(ezcodingSecurityConfigBean.getDefaultFailureUrl()).ifPresent(url -> multipleAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler(url)));
        multipleAuthenticationFilter.setAuthenticationService(compositeAuthenticationService());
        multipleAuthenticationFilter.setContinueChainBeforeSuccessfulAuthentication(true);
        multipleAuthenticationFilter.setAllowSessionCreation(false);
        multipleAuthenticationFilter.setAuthenticationSuccessHandler(successHandlerChain);
        return multipleAuthenticationFilter;
    }

    @Bean
    @ConditionalOnMissingBean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        PasswordEncoder passwordEncoder = passwordEncoder();

        AccountPasswordAuthenticationProvider accountPasswordAuthenticationProvider = new AccountPasswordAuthenticationProvider(customUserDetailsService, passwordEncoder);
        AccountPasswordVerificationAuthenticationProvider accountPasswordVerificationAuthenticationProvider = new AccountPasswordVerificationAuthenticationProvider(customUserDetailsService, imageVerificationService, passwordEncoder);
        PhoneMessageCodeAuthenticationProvider phoneMessageCodeAuthenticationProvider = new PhoneMessageCodeAuthenticationProvider(customUserDetailsService, numberVerificationService);
        PhonePasswordAuthenticationProvider phonePasswordAuthenticationProvider = new PhonePasswordAuthenticationProvider(customUserDetailsService, passwordEncoder);
        NoLimitAuthenticationProvider noLimitAuthenticationProvider = new NoLimitAuthenticationProvider(customUserDetailsService);

        auth
                .authenticationProvider(accountPasswordAuthenticationProvider)
                .authenticationProvider(accountPasswordVerificationAuthenticationProvider)
                .authenticationProvider(phoneMessageCodeAuthenticationProvider)
                .authenticationProvider(phonePasswordAuthenticationProvider)
                .authenticationProvider(noLimitAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        MultipleAuthenticationFilter multipleAuthenticationFilter = multipleAuthenticationFilter();

        http
                .addFilterAfter(multipleAuthenticationFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .logout().disable()
                .csrf().disable()
                .formLogin().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CompositeAuthenticationServiceImpl compositeAuthenticationService() throws Exception {
        AuthenticationManager authenticationManager = authenticationManagerBean();
        PasswordEncoder passwordEncoder = passwordEncoder();

        AccountPasswordAuthenticationServiceImpl accountPasswordAuthenticationService = new AccountPasswordAuthenticationServiceImpl(authenticationManager, basicUserService, passwordEncoder);
        PhonePasswordAuthenticationServiceImpl phonePasswordAuthenticationService = new PhonePasswordAuthenticationServiceImpl(authenticationManager, basicUserService, passwordEncoder);
        PhoneMessageCodeAuthenticationServiceImpl phoneMessageCodeAuthenticationService = new PhoneMessageCodeAuthenticationServiceImpl(authenticationManager, basicUserService, numberVerificationService);
        AccountPasswordVerificationAuthenticationServiceImpl accountPasswordVerificationAuthenticationService = new AccountPasswordVerificationAuthenticationServiceImpl(authenticationManager, basicUserService, imageVerificationService);
        NoLimitAuthenticationServiceImpl noLimitAuthenticationService = new NoLimitAuthenticationServiceImpl(authenticationManager, basicUserService);

        CompositeAuthenticationServiceImpl compositeAuthenticationService = new CompositeAuthenticationServiceImpl();

        compositeAuthenticationService.loginService(LoginRegisterTypeEnum.ACCOUNT_PASSWORD, accountPasswordAuthenticationService);
        compositeAuthenticationService.loginService(LoginRegisterTypeEnum.PHONE_PASSWORD, phonePasswordAuthenticationService);
        compositeAuthenticationService.loginService(LoginRegisterTypeEnum.PHONE_MESSAGE_CODE, phoneMessageCodeAuthenticationService);
        compositeAuthenticationService.loginService(LoginRegisterTypeEnum.ACCOUNT_PASSWORD_VERIFICATION_CODE, accountPasswordVerificationAuthenticationService);
        compositeAuthenticationService.loginService(LoginRegisterTypeEnum.REGISTER, noLimitAuthenticationService);

        compositeAuthenticationService.registerService(LoginRegisterTypeEnum.ACCOUNT_PASSWORD, accountPasswordAuthenticationService);
        compositeAuthenticationService.registerService(LoginRegisterTypeEnum.PHONE_PASSWORD, phonePasswordAuthenticationService);
        compositeAuthenticationService.registerService(LoginRegisterTypeEnum.PHONE_MESSAGE_CODE, phoneMessageCodeAuthenticationService);
        compositeAuthenticationService.registerService(LoginRegisterTypeEnum.ACCOUNT_PASSWORD_VERIFICATION_CODE, accountPasswordVerificationAuthenticationService);

        return compositeAuthenticationService;
    }

}
