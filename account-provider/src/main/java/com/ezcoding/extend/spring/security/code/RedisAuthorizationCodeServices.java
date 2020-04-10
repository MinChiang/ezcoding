package com.ezcoding.extend.spring.security.code;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-15 18:54
 */
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private static final String DEFAULT_PREFIX = "";

    private String prefix = DEFAULT_PREFIX;
    private RedisTemplate<String, OAuth2Authentication> redisTemplate;
    private int defaultExpireTime = 60 * 15;

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        redisTemplate.opsForValue().set(prefix + code, authentication, defaultExpireTime, TimeUnit.SECONDS);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        String key = prefix + code;
        OAuth2Authentication oAuth2Authentication = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        return oAuth2Authentication;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public RedisTemplate<String, OAuth2Authentication> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, OAuth2Authentication> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public int getDefaultExpireTime() {
        return defaultExpireTime;
    }

    public void setDefaultExpireTime(int defaultExpireTime) {
        this.defaultExpireTime = defaultExpireTime;
    }

}
