package com.ezcoding.common.security.vote.voter;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-17 17:41
 */
public class DynamicRoleVoter extends RoleVoter {

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        return super.vote(authentication, object, attributes);
    }

}
