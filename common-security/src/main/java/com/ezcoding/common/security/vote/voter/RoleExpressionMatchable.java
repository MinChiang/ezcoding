package com.ezcoding.common.security.vote.voter;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-18 12:59
 */
public interface RoleExpressionMatchable {

    /**
     * 判断是否符合表达式
     *
     * @param authorities 权限内容
     * @return 是否符合表达式
     */
    boolean match(Collection<? extends GrantedAuthority> authorities);

}
