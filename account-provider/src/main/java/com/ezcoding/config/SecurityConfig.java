package com.ezcoding.config;

import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;
import com.ezcoding.common.foundation.core.tools.uuid.IUUIDProducer;
import com.ezcoding.common.foundation.core.tools.verification.NumberVerificationCodeGenerator;
import com.ezcoding.common.foundation.core.tools.verification.OriginalVerificationCodeGenerator;
import com.ezcoding.extend.spring.security.failureHandler.CustomAuthenticationFailureHandler;
import com.ezcoding.extend.spring.security.filter.MultipleAuthenticationFilter;
import com.ezcoding.extend.spring.security.provider.*;
import com.ezcoding.extend.spring.security.successHandler.LoginRecordSuccessHandler;
import com.ezcoding.extend.spring.security.successHandler.SuccessHandlerChain;
import com.ezcoding.extend.user.LocalUserProxy;
import com.ezcoding.module.management.service.RoleService;
import com.ezcoding.module.user.bean.model.VerificationInfo;
import com.ezcoding.module.user.core.authentication.*;
import com.ezcoding.module.user.core.verification.RedisVerificationServiceImpl;
import com.ezcoding.module.user.dao.LoginInfoMapper;
import com.ezcoding.module.user.dao.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-25 0:36
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private RoleService roleService;
    @Resource(name = "imageVerificationService")
    private RedisVerificationServiceImpl imageVerificationService;
    @Resource(name = "numberVerificationService")
    private RedisVerificationServiceImpl numberVerificationService;
    @Autowired
    private IBasicUserService basicUserService;
    @Autowired
    private ICustomUserDetailsService customUserDetailsService;

    @Bean
    public RedisTemplate<String, VerificationInfo> verificationInfoRedisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
        RedisTemplate<String, VerificationInfo> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<VerificationInfo> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<VerificationInfo>(VerificationInfo.class);
        objectJackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        return template;
    }

    @Bean(name = "imageVerificationService")
    public RedisVerificationServiceImpl imageVerificationService(RedisTemplate<String, VerificationInfo> verificationInfoRedisTemplate, OriginalVerificationCodeGenerator originalVerificationCodeGenerator) {
        RedisVerificationServiceImpl redisVerificationServiceImpl = new RedisVerificationServiceImpl();
        redisVerificationServiceImpl.setRedisTemplate(verificationInfoRedisTemplate);
        redisVerificationServiceImpl.setVerificationCodeGenerator(originalVerificationCodeGenerator);
        return redisVerificationServiceImpl;
    }

    @Bean(name = "numberVerificationService")
    public RedisVerificationServiceImpl numberVerificationService(RedisTemplate<String, VerificationInfo> verificationInfoRedisTemplate, NumberVerificationCodeGenerator numberVerificationCodeGenerator, IUUIDProducer producer) {
        RedisVerificationServiceImpl redisVerificationServiceImpl = new RedisVerificationServiceImpl();
        redisVerificationServiceImpl.setRedisTemplate(verificationInfoRedisTemplate);
        redisVerificationServiceImpl.setVerificationCodeGenerator(numberVerificationCodeGenerator);
        redisVerificationServiceImpl.setReceiptProducer(producer);
        return redisVerificationServiceImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        AccountPasswordAuthenticationProvider accountPasswordAuthenticationProvider = new AccountPasswordAuthenticationProvider();
        accountPasswordAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        accountPasswordAuthenticationProvider.setUserDetailsService(customUserDetailsService);

        AccountPasswordVerificationAuthenticationProvider accountPasswordVerificationAuthenticationProvider = new AccountPasswordVerificationAuthenticationProvider();
        accountPasswordVerificationAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        accountPasswordVerificationAuthenticationProvider.setImageVerificationService(imageVerificationService);
        accountPasswordVerificationAuthenticationProvider.setUserDetailsService(customUserDetailsService);

        PhoneMessageCodeAuthenticationProvider phoneMessageCodeAuthenticationProvider = new PhoneMessageCodeAuthenticationProvider();
        phoneMessageCodeAuthenticationProvider.setNumberVerificationService(numberVerificationService);
        phoneMessageCodeAuthenticationProvider.setUserDetailsService(customUserDetailsService);

        PhonePasswordAuthenticationProvider phonePasswordAuthenticationProvider = new PhonePasswordAuthenticationProvider();
        phonePasswordAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        phonePasswordAuthenticationProvider.setUserDetailsService(customUserDetailsService);

        NoLimitAuthenticationProvider noLimitAuthenticationProvider = new NoLimitAuthenticationProvider();
        noLimitAuthenticationProvider.setUserDetailsService(customUserDetailsService);

        auth
                .authenticationProvider(accountPasswordAuthenticationProvider)
                .authenticationProvider(accountPasswordVerificationAuthenticationProvider)
                .authenticationProvider(phoneMessageCodeAuthenticationProvider)
                .authenticationProvider(phonePasswordAuthenticationProvider)
                .authenticationProvider(noLimitAuthenticationProvider);
    }

    private MultipleAuthenticationFilter multipleAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        CustomAuthenticationFailureHandler customAuthenticationFailureHandler = new CustomAuthenticationFailureHandler("http://www.zhihu.com");

        SuccessHandlerChain successHandlerChain = new SuccessHandlerChain();
        successHandlerChain.addAuthenticationSuccessHandler(new LoginRecordSuccessHandler(loginInfoMapper));
        successHandlerChain.addAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler());

        MultipleAuthenticationFilter multipleAuthenticationFilter = new MultipleAuthenticationFilter("/oauth/authorize");
        multipleAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        multipleAuthenticationFilter.setAuthenticationService(compositeAuthenticationService());
        multipleAuthenticationFilter.setContinueChainBeforeSuccessfulAuthentication(true);
        multipleAuthenticationFilter.setAllowSessionCreation(false);
        multipleAuthenticationFilter.setAuthenticationSuccessHandler(successHandlerChain);
        return multipleAuthenticationFilter;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager();
        MultipleAuthenticationFilter multipleAuthenticationFilter = multipleAuthenticationFilter(authenticationManager);

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

    /**
     * 覆盖对应获取用户信息的远程调用
     *
     * @return 用户代理
     */
    @Bean
    public LocalUserProxy localUserProxy(UserMapper userMapper) {
        return new LocalUserProxy(userMapper);
    }

}
