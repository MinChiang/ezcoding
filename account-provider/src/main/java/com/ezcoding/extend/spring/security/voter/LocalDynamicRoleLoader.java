package com.ezcoding.extend.spring.security.voter;

import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import com.ezcoding.common.security.vote.voter.IDynamicRoleLoadable;
import com.ezcoding.redis.serializer.StandardRedisKey;
import com.ezcoding.redis.serializer.StandardRedisKeyBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;

import java.util.HashMap;
import java.util.Map;

import static com.ezcoding.module.permission.exception.AccountPermissionModuleConstants.PERMISSION_PERSIST;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-22 11:37
 */
public class LocalDynamicRoleLoader implements IDynamicRoleLoadable {

    private String applicationName;
    private RedisTemplate<StandardRedisKey, String> redisTemplate;
    private StandardRedisKeyBuilder builder = StandardRedisKeyBuilder.create(PERMISSION_PERSIST);

    public LocalDynamicRoleLoader(String applicationName, RedisTemplate<StandardRedisKey, String> redisTemplate) {
        this.applicationName = applicationName.toUpperCase();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Map<ConfigAttribute, String> load() {
        Map<ConfigAttribute, String> result = new HashMap<>();

        Map<DynamicConfigAttribute, String> entries = redisTemplate.<DynamicConfigAttribute, String>opsForHash().entries(builder.build(DynamicConfigAttribute.PREFIX + this.applicationName));
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

}
