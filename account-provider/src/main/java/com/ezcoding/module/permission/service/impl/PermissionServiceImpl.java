package com.ezcoding.module.permission.service.impl;

import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import com.ezcoding.common.security.vote.voter.IDynamicRoleLoadable;
import com.ezcoding.module.permission.service.IPermissionService;
import com.ezcoding.redis.serializer.StandardRedisKey;
import com.ezcoding.redis.serializer.StandardRedisKeyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    private RedisTemplate<StandardRedisKey, String> redisTemplate;
    private StandardRedisKeyBuilder builder = StandardRedisKeyBuilder.create(PERMISSION_PERSIST);

    @Override
    public Map<ConfigAttribute, String> load() {
        return this.loadPermissions(this.applicationName);
    }

    @Override
    public boolean hasPermission(String expression, Collection<? extends GrantedAuthority> authorities) {
        return false;
    }

    @Override
    public void registerPermission(DynamicConfigAttribute dynamicConfigAttribute, String expression) {

    }

    @Override
    public Map<ConfigAttribute, String> loadPermissions(String applicationName) {
        Map<ConfigAttribute, String> result = new HashMap<>();

        Map<DynamicConfigAttribute, String> entries = redisTemplate.<DynamicConfigAttribute, String>opsForHash().entries(builder.build(DynamicConfigAttribute.PREFIX + applicationName));
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

}
