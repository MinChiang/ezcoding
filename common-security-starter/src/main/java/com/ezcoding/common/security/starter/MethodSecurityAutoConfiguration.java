package com.ezcoding.common.security.starter;

import com.ezcoding.common.security.metadatasource.DynamicAnnotationSecurityMetadataSource;
import com.ezcoding.common.security.vote.voter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.vote.ScopeVoter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-27 10:55
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class MethodSecurityAutoConfiguration extends GlobalMethodSecurityConfiguration {

    @Value("${spring.application.name}")
    private String applicationName = "";
    @Autowired
    private EzcodingSecurityConfigBean ezcodingSecurityConfigBean;
    @Autowired(required = false)
    private DynamicRoleVoter dynamicRoleVoter;

    @Override
    protected AccessDecisionManager accessDecisionManager() {
        AbstractAccessDecisionManager accessDecisionManager = (AbstractAccessDecisionManager) super.accessDecisionManager();
        List<AccessDecisionVoter<?>> decisionVoters = accessDecisionManager.getDecisionVoters();

        //加入oauth2scope的投票器
        decisionVoters.add(new ScopeVoter());
        decisionVoters.add(new LoginInfoVoter());

        if (ezcodingSecurityConfigBean.isEnableDynamicRole() && dynamicRoleVoter != null) {
            decisionVoters.add(dynamicRoleVoter);
        }

        return accessDecisionManager;
    }

    @Bean
    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return new DynamicAnnotationSecurityMetadataSource(applicationName);
    }

    @ConditionalOnMissingBean(DynamicRoleVoter.class)
    private static class DynamicRoleConfiguration {

        @Autowired
        private EzcodingSecurityConfigBean ezcodingSecurityConfigBean;
        @Value("${spring.application.name}")
        private String applicationName = "";

        @Bean
        public DynamicRoleVoter dynamicRoleVoter() {
            return new DynamicRoleVoter();
        }

        @Bean
        @ConditionalOnMissingBean(DynamicRoleLoadable.class)
        public DynamicRoleLoadable dynamicRoleLoadable(DynamicRoleVoter dynamicRoleVoter,
                                                       DynamicAnnotationSecurityMetadataSource dynamicAnnotationSecurityMetadataSource) {
            List<DynamicRoleLoadable> loaders = new ArrayList<>();
            //需要注意注册的先后顺序，后面注册的会覆盖之前注册的内容
            if (ezcodingSecurityConfigBean.getDynamicRoleLoadYaml() != null && !ezcodingSecurityConfigBean.getDynamicRoleLoadYaml().isEmpty()) {
                loaders.add(new LocalFileDynamicRoleLoader(ezcodingSecurityConfigBean.getDynamicRoleLoadYaml(), this.applicationName));
            }
            if (ezcodingSecurityConfigBean.getDynamicRoleLoadUrl() != null && !ezcodingSecurityConfigBean.getDynamicRoleLoadUrl().isEmpty()) {
                loaders.add(new SimpleRemoteDynamicRoleLoader(this.applicationName, ezcodingSecurityConfigBean.getDynamicRoleLoadUrl(), dynamicAnnotationSecurityMetadataSource));
            }

            DynamicRoleLoadable delegateDynamicRoleLoader = new DelegateDynamicRoleLoader(loaders);
            if (ezcodingSecurityConfigBean.isEnableAutoLoader()) {
                return new DynamicSecheduledTriggerProxy(delegateDynamicRoleLoader, dynamicRoleVoter).config(true, ezcodingSecurityConfigBean.getRefreshSeconds());
            }
            return delegateDynamicRoleLoader;
        }

    }

}
