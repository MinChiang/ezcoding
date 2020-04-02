package com.ezcoding.common.security.starter;

import com.ezcoding.common.security.vote.voter.LoginInfoVoter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.vote.ScopeVoter;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-27 10:55
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class MethodSecurityAutoConfiguration extends GlobalMethodSecurityConfiguration {

    @Override
    protected AccessDecisionManager accessDecisionManager() {
        AbstractAccessDecisionManager accessDecisionManager = (AbstractAccessDecisionManager) super.accessDecisionManager();
        List<AccessDecisionVoter<?>> decisionVoters = accessDecisionManager.getDecisionVoters();

        //加入oauth2scope的投票器
        decisionVoters.add(new ScopeVoter());
        decisionVoters.add(new LoginInfoVoter());

        return accessDecisionManager;
    }

}
