package com.ezcoding.base.web.extend.spring.security.authority;

import com.ezcoding.base.web.extend.spring.security.authentication.UserAuthentication;
import com.ezcoding.common.foundation.core.constant.GlobalConstants;
import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.ezcoding.sdk.account.user.bean.model.DeviceTypeEnum;
import com.ezcoding.sdk.account.user.bean.model.LoginRegisterTypeEnum;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-05-29 14:14
 */
public class CustomUserAuthenticationConverter implements UserAuthenticationConverter {

    private final String PRINCIPAL = "principal";
    private final String DETAIL = "detail";

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Object principal;
        if (authentication instanceof UserAuthentication) {
            UserAuthentication userAuthentication = (UserAuthentication) authentication;
            principal = userAuthentication.getPrincipal();
        } else {
            principal = authentication.getName();
        }

        Map<String, Object> response = new LinkedHashMap<>();
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        Object details = authentication.getDetails();
        if (details != null) {
            if (details instanceof Map) {
                details = this.convertUserDetails((Map<?, ?>) details);
            }
            response.put(DETAIL, details);
        }
        response.put(PRINCIPAL, principal);

        return response;
    }

    /**
     * 解析其他额外信息
     *
     * @param map 其他额外信息
     * @return 解析后的信息
     */
    protected Map<String, Object> convertUserDetails(Map<?, ?> map) {
        Map<String, Object> result = Maps.newHashMap();
        map.forEach((key, value) -> result.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key.toString()), value));
        return result;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(PRINCIPAL)) {
            Object principal = map.get(PRINCIPAL);
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            Object details = null;
            LoginRegisterTypeEnum loginType = null;
            DeviceTypeEnum deviceType = null;

            if (map.containsKey(DETAIL)) {
                Map<String, Object> content = this.extractUserDetails((Map<String, ?>) map.get(DETAIL));
                loginType = EnumMappableUtils.mapIgnoreType(content.get(GlobalConstants.Context.LOGIN_TYPE), LoginRegisterTypeEnum.class);
                deviceType = EnumMappableUtils.mapIgnoreType(content.get(GlobalConstants.Context.DEVICE_TYPE), DeviceTypeEnum.class);
                details = content;
            }

            UserAuthentication userAuthentication = new UserAuthentication(principal.toString(), authorities, true);
            userAuthentication.setLoginType(loginType == null ? LoginRegisterTypeEnum.UNKNOWN : loginType);
            userAuthentication.setDeviceType(deviceType == null ? DeviceTypeEnum.UNKNOWN : deviceType);
            userAuthentication.setDetails(details);
            return userAuthentication;
        }
        return null;
    }

    /**
     * 解析其他额外信息
     *
     * @param map 其他额外信息
     * @return 解析后的信息
     */
    protected Map<String, Object> extractUserDetails(Map<String, ?> map) {
        Map<String, Object> result = Maps.newHashMap();
        map.forEach((key, value) -> result.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key), value));
        return result;
    }

    /**
     * 获取权限
     *
     * @param map 权限来源
     * @return 用户所拥有的权限列表
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES)) {
            return new ArrayList<>(0);
        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }

}
