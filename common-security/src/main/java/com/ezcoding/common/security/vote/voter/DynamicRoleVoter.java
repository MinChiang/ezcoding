package com.ezcoding.common.security.vote.voter;

import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-17 17:41
 */
public class DynamicRoleVoter implements AccessDecisionVoter<Object> {

    private ConcurrentMap<ConfigAttribute, >

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof DynamicConfigAttribute;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        int result = ACCESS_ABSTAIN;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute attribute : attributes) {
            if (!this.supports(attribute)) {
                continue;
            }

            result = ACCESS_DENIED;

            String attr = attribute.getAttribute();
            if (loginTypeStr.equals(attr) || deviceTypeStr.equals(attr)) {
                return ACCESS_GRANTED;
            }

        }
        return 0;
    }

}
