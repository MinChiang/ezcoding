package com.ezcoding.common.security.vote.voter;

import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Collection;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-17 17:41
 */
public class DynamicRoleVoter implements AccessDecisionVoter<Object> {

    private Map<ConfigAttribute, ExpressionMatcher> matcherHandlers = new ConcurrentReferenceHashMap<>();

    public DynamicRoleVoter() {
    }

    public DynamicRoleVoter(Map<ConfigAttribute, String> matcherHandlers) {
        this.addExpressionHandlers(matcherHandlers);
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute.getAttribute().startsWith(DynamicConfigAttribute.PREFIX);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        int result = ACCESS_ABSTAIN;

        for (ConfigAttribute attribute : attributes) {
            if (!this.supports(attribute)) {
                continue;
            }

            result = ACCESS_DENIED;
            ExpressionMatcher expressionMatcher = matcherHandlers.get(attribute);
            if (expressionMatcher != null && expressionMatcher.match(authentication.getAuthorities())) {
                return ACCESS_GRANTED;
            }
        }

        return result;
    }

    /**
     * 增加匹配器
     *
     * @param matcherHandlers 需要增加的匹配器
     */
    public void addExpressionHandlers(Map<ConfigAttribute, String> matcherHandlers) {
        if (matcherHandlers == null || matcherHandlers.isEmpty()) {
            return;
        }
        matcherHandlers.forEach((key, value) -> this.matcherHandlers.put(key, new ExpressionMatcher(value)));
    }

}
