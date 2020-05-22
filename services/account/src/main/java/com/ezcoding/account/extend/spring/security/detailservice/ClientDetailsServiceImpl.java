package com.ezcoding.account.extend.spring.security.detailservice;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ezcoding.account.extend.spring.security.CustomClientDetails;
import com.ezcoding.account.module.user.dao.ClientDetailsMapper;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.google.common.base.Splitter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-29 17:33
 */
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private static final String DEFAULT_PREFIX = "";

    private String prefix = DEFAULT_PREFIX;
    private ClientDetailsMapper clientDetailsMapper;
    private RedisTemplate<String, ClientDetails> redisTemplate;
    private boolean useCache = false;
    private int defaultExpireTime = 60 * 15;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails clientDetails = null;
        if (useCache) {
            clientDetails = redisTemplate.opsForValue().get(prefix + clientId);
        }

        if (clientDetails == null) {
            CustomClientDetails customClientDetails = new CustomClientDetails();
            customClientDetails.setClientName(clientId);
            customClientDetails = clientDetailsMapper.selectOne(Wrappers.query(customClientDetails));
            AssertUtils.mustNotNull(customClientDetails, UserExceptionConstants.OAUTH_VALID_ERROR.instance().param("cliendId对应的客户端未注册").build());

            clientDetails = convert(customClientDetails);
            if (useCache) {
                redisTemplate.opsForValue().set(prefix + clientId, clientDetails, defaultExpireTime, TimeUnit.SECONDS);
            }
        }

        return clientDetails;
    }

    /**
     * 转换
     *
     * @param customClientDetails 原始对象
     * @return 目标对象
     */
    private ClientDetails convert(CustomClientDetails customClientDetails) {
        BaseClientDetails baseClientDetails = new BaseClientDetails(
                customClientDetails.getClientName(),
                customClientDetails.getResourceIds(),
                customClientDetails.getScopes(),
                customClientDetails.getAuthorizedGrantTypes(),
                null,
                customClientDetails.getRegisteredRedirectUris()
        );

        baseClientDetails.setAccessTokenValiditySeconds(customClientDetails.getAccessTokenValiditySeconds());
        baseClientDetails.setRefreshTokenValiditySeconds(customClientDetails.getRefreshTokenValiditySeconds());
        baseClientDetails.setClientSecret(customClientDetails.getClientSecret());

        String autoApproveScopes = customClientDetails.getAutoApproveScopes();
        if (StringUtils.isNotEmpty(autoApproveScopes)) {
            baseClientDetails.setAutoApproveScopes(Splitter.on(",").splitToList(autoApproveScopes));
        }

        return baseClientDetails;
    }

    public ClientDetailsMapper getClientDetailsMapper() {
        return clientDetailsMapper;
    }

    public void setClientDetailsMapper(ClientDetailsMapper clientDetailsMapper) {
        this.clientDetailsMapper = clientDetailsMapper;
    }

    public RedisTemplate<String, ClientDetails> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, ClientDetails> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.useCache = true;
    }

    public long getDefaultExpireTime() {
        return defaultExpireTime;
    }

    public void setDefaultExpireTime(int defaultExpireTime) {
        this.defaultExpireTime = defaultExpireTime;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isUseCache() {
        return useCache;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

}
