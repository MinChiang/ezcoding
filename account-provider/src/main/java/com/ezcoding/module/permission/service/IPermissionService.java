package com.ezcoding.module.permission.service;

import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-22 20:25
 */
public interface IPermissionService {

    /**
     * 测试是否满足对应的权限表达式
     *
     * @param expression  需要判断的表达式
     * @param authorities 测试的角色数组
     * @return 是否满足对应的表达式
     */
    boolean hasPermission(String expression, Collection<? extends GrantedAuthority> authorities);

    /**
     * 为动态接口注册对应的数据权限表达式
     *
     * @param dynamicConfigAttribute 数据接口
     * @param expression             注册的表达式
     */
    void registerPermission(DynamicConfigAttribute dynamicConfigAttribute, String expression);

    /**
     * 加载对应微服务所属权限
     *
     * @param applicationName 微服务名称
     * @return 微服务所属权限
     */
    Map<ConfigAttribute, String> loadPermissions(String applicationName);

}
