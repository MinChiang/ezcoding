package com.ezcoding.common.security.authority;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;
import com.ezcoding.common.core.enums.EnumMappableUtils;
import com.ezcoding.common.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.common.security.authentication.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-05-29 14:14
 */
public class CustomUserAuthenticationConverter implements UserAuthenticationConverter {

    private static final char UNDERSCORE = '_';

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
        Map<String, Object> result = new HashMap<>();
        map.forEach((key, value) -> result.put(camelCaseTolowerUnderscore(key.toString()), value));
        return result;
    }

    /**
     * 驼峰转下划线分割
     *
     * @param camelCase 驼峰形式的字符串
     * @return 下划线形式的字符串
     */
    protected static String camelCaseTolowerUnderscore(String camelCase) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERSCORE).append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(PRINCIPAL)) {
            Object principal = map.get(PRINCIPAL);
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            Object details = null;
            LoginRegisterTypeEnum loginType = LoginRegisterTypeEnum.UNKNOWN;
            DeviceTypeEnum deviceType = DeviceTypeEnum.UNKNOWN;

            if (map.containsKey(DETAIL)) {
                Map<String, Object> content = this.extractUserDetails((Map<String, ?>) map.get(DETAIL));
                loginType = EnumMappableUtils.mapIgnoreType(content.get(AbstractLoginInfoPreservableAuthentication.LOGIN_TYPE_KEY), LoginRegisterTypeEnum.class);
                deviceType = EnumMappableUtils.mapIgnoreType(content.get(AbstractLoginInfoPreservableAuthentication.DEVICE_TYPE_KEY), DeviceTypeEnum.class);
                details = content;
            }

            UserAuthentication userAuthentication = new UserAuthentication(Long.valueOf(principal.toString()), authorities, true);
            userAuthentication.setLoginType(loginType);
            userAuthentication.setDeviceType(deviceType);
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
        Map<String, Object> result = new HashMap<>();
        map.forEach((key, value) -> result.put(lowerUnderscoreToCamelCase(key), value));
        return result;
    }

    /**
     * 下划线分割转驼峰
     *
     * @param lowerUnderscore 下划线分割的字符串
     * @return 驼峰形式的字符串
     */
    protected static String lowerUnderscoreToCamelCase(String lowerUnderscore) {
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lowerUnderscore.length(); i++) {
            char c = lowerUnderscore.charAt(i);
            if (UNDERSCORE == c) {
                flag = true;
                continue;
            }
            if (flag) {
                flag = false;
                c = Character.toUpperCase(c);
            }
            sb.append(c);
        }
        return sb.toString();
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
        throw new IllegalArgumentException("权限列表不正确");
    }

}
