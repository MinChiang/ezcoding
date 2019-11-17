package com.ezcoding.auth.module.resource.core.expression;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-06 23:22
 */
public interface IRoleExpressionMatchable {

    /**
     * 判断是否满足对应的表达式
     *
     * @param expression 需要判断的表达式
     * @param roles      用户拥有的角色列表
     * @return 是否满足
     */
    boolean match(String expression, Collection<String> roles);

    /**
     * 以或方式扩展表达式
     *
     * @param srcExpression 原有的表达式
     * @param roles         待添加的角色列表
     * @return 扩展后的表达式
     */
    String or(String srcExpression, Collection<String> roles);

    /**
     * 以与方式扩展表达式
     *
     * @param srcExpression 原有的表达式
     * @param roles         待添加的角色列表
     * @return 扩展后的表达式
     */
    String and(String srcExpression, Collection<String> roles);

}
