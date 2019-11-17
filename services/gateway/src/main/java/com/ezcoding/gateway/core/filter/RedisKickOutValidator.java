package com.ezcoding.gateway.core.filter;

import com.ezcoding.sdk.account.user.core.AbstractKickOutValidator;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-07 14:33
 */
public class RedisKickOutValidator extends AbstractKickOutValidator {

    private static final String DEFAULT_KICK_OUT_PREFIX = "account:kick_out:";

    private String kickOutPrefix = DEFAULT_KICK_OUT_PREFIX;
    private RedisTemplate<String, Date> redisTemplate;

    public RedisKickOutValidator(String kickOutPrefix, RedisTemplate<String, Date> redisTemplate) {
        this.kickOutPrefix = kickOutPrefix;
        this.redisTemplate = redisTemplate;
    }

    public RedisKickOutValidator(RedisTemplate<String, Date> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Date accquireKickOutTime(String userCode) {
        return redisTemplate.opsForValue().get(kickOutPrefix + userCode);
    }

    public String getKickOutPrefix() {
        return kickOutPrefix;
    }

    public void setKickOutPrefix(String kickOutPrefix) {
        this.kickOutPrefix = kickOutPrefix;
    }

    public RedisTemplate<String, Date> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Date> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
