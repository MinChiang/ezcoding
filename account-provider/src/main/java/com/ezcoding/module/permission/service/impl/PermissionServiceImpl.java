package com.ezcoding.module.permission.service.impl;

import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import com.ezcoding.common.security.vote.voter.ExpressionMatcher;
import com.ezcoding.common.security.vote.voter.IDynamicRoleLoadable;
import com.ezcoding.module.permission.service.IPermissionService;
import com.ezcoding.redis.serializer.StandardRedisKey;
import com.ezcoding.redis.serializer.StandardRedisKeyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_PARAM_VALIDATE_ERROR;
import static com.ezcoding.module.permission.exception.AccountPermissionExceptionConstants.GEN_PERMISSION_EXPRESSION_ERROR;
import static com.ezcoding.module.permission.exception.AccountPermissionModuleConstants.PERMISSION_PERSIST;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-28 17:12
 */
@Service
public class PermissionServiceImpl implements IPermissionService, IDynamicRoleLoadable {

    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    @Qualifier("dynamicConfigAttributeExpressionRedisTemplate")
    private RedisTemplate<StandardRedisKey, String> redisTemplate;
    private StandardRedisKeyBuilder builder = StandardRedisKeyBuilder.create(PERMISSION_PERSIST);

    @Override
    public Map<DynamicConfigAttribute, String> load() {
        return this.loadPermissions(this.applicationName);
    }

    @Override
    public boolean hasPermission(String expression, Collection<? extends GrantedAuthority> authorities) {
        ExpressionMatcher expressionMatcher = this.checkAndBuildExpressionMatcher(expression);
        return expressionMatcher.match(authorities);
    }

    @Override
    public void registerPermission(DynamicConfigAttribute dynamicConfigAttribute, String expression) {
        AssertUtils.mustNotNull(new Object[]{dynamicConfigAttribute, expression}, () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());
        this.checkAndBuildExpressionMatcher(expression);
        this.redisTemplate.opsForHash().put(this.acquireStandardKey(dynamicConfigAttribute.getApplicationName()), dynamicConfigAttribute.getAttribute(), expression);
    }

    @Override
    public Map<DynamicConfigAttribute, String> loadPermissions(String applicationName) {
        Map<DynamicConfigAttribute, String> result = new HashMap<>();

        Map<DynamicConfigAttribute, String> entries = redisTemplate.<DynamicConfigAttribute, String>opsForHash().entries(this.acquireStandardKey(applicationName));
        for (Map.Entry<DynamicConfigAttribute, String> entry : entries.entrySet()) {
            DynamicConfigAttribute key = entry.getKey();
            String value = entry.getValue();
            if (key == null || value == null) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    @Override
    public void registerInferablePermissions(String applicationName, Set<DynamicConfigAttribute> dynamicConfigAttributes) {
        //过滤已经被注册的接口
        Map<DynamicConfigAttribute, String> dynamicConfigAttributeStringMap = this.loadPermissions(applicationName);
        dynamicConfigAttributes.removeAll(dynamicConfigAttributeStringMap.keySet());
    }

    /**
     * 获取对应的redis主键键值
     *
     * @param applicationName 应用名称
     * @return 主键键值
     */
    private StandardRedisKey acquireStandardKey(String applicationName) {
        return builder.build(DynamicConfigAttribute.PREFIX + Optional.of(applicationName).map(String::toUpperCase).get());
    }

    /**
     * 校验并且构建相应的表达式
     *
     * @param originalExpression 原生表达式
     * @return 构建的表达式对象
     */
    private ExpressionMatcher checkAndBuildExpressionMatcher(String originalExpression) {
        //校验表达式的正确性
        try {
            return new ExpressionMatcher(originalExpression);
        } catch (Exception e) {
            throw WebExceptionBuilderFactory.webExceptionBuilder(GEN_PERMISSION_EXPRESSION_ERROR).build();
        }
    }

}
