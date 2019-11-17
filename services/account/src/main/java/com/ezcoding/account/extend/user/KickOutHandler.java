package com.ezcoding.account.extend.user;

import com.ezcoding.sdk.account.user.core.AbstractKickOutValidator;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-07 15:56
 */
public class KickOutHandler extends AbstractKickOutValidator {

    private static final String DEFAULT_KICK_OUT_PREFIX = "account:kick_out:";

    private String kickOutPrefix = DEFAULT_KICK_OUT_PREFIX;
    private RedisTemplate<String, Date> redisTemplate;
    private long expireSecords;

    public KickOutHandler(String kickOutPrefix, RedisTemplate<String, Date> redisTemplate, long expireSecords) {
        this.kickOutPrefix = kickOutPrefix;
        this.redisTemplate = redisTemplate;
        this.expireSecords = expireSecords;
    }

    public KickOutHandler(RedisTemplate<String, Date> redisTemplate, long expireSecords) {
        this.redisTemplate = redisTemplate;
        this.expireSecords = expireSecords;
    }

    /**
     * 踢出用户
     *
     * @param userCode 待踢出的用户
     */
    public void kickOut(String userCode) {
        redisTemplate.opsForValue().set(kickOutPrefix + userCode, new Date(), expireSecords, TimeUnit.SECONDS);
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
