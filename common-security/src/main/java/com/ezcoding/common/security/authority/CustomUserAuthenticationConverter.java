package com.ezcoding.common.security.authority;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;
import com.ezcoding.common.foundation.core.enums.EnumMappableUtils;
import com.ezcoding.common.security.authentication.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-05-29 14:14
 */
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    private static final char UNDERSCORE = '_';
    private static final String PRINCIPAL = "principal";
    private static final String DETAIL = "detail";
    private static final String LOGIN_TYPE = "login_type";
    private static final String DEVICE_TYPE = "device_type";

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();

        // 用户基本信息
        Object principal;
        int loginType;
        int deviceType;
        if (authentication instanceof UserAuthentication) {
            UserAuthentication userAuthentication = (UserAuthentication) authentication;
            principal = userAuthentication.getPrincipal();
            loginType = Optional.ofNullable(userAuthentication.getLoginType()).orElse(LoginRegisterTypeEnum.UNKNOWN).id;
            deviceType = Optional.ofNullable(userAuthentication.getDeviceType()).orElse(DeviceTypeEnum.UNKNOWN).id;
            response.put(PRINCIPAL, principal);
            response.put(LOGIN_TYPE, loginType);
            response.put(DEVICE_TYPE, deviceType);
        } else {
            principal = authentication.getName();
            response.put(PRINCIPAL, principal);
        }

        // 权限列表
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }

        // 额外信息
        Object details = authentication.getDetails();
        if (details != null) {
            if (details instanceof Map) {
                details = this.convertUserDetails((Map<?, ?>) details);
            }
            response.put(DETAIL, details);
        }

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
        map.forEach((key, value) -> result.put(camelCaseToLowerUnderscore(key.toString()), value));
        return result;
    }

    /**
     * 驼峰转下划线分割
     *
     * @param camelCase 驼峰形式的字符串
     * @return 下划线形式的字符串
     */
    protected static String camelCaseToLowerUnderscore(String camelCase) {
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

            LoginRegisterTypeEnum loginType = Optional.ofNullable(map.get(LOGIN_TYPE)).map(type -> EnumMappableUtils.map(type, LoginRegisterTypeEnum.class)).orElse(LoginRegisterTypeEnum.UNKNOWN);
            DeviceTypeEnum deviceType = Optional.ofNullable(map.get(DEVICE_TYPE)).map(type -> EnumMappableUtils.map(type, DeviceTypeEnum.class)).orElse(DeviceTypeEnum.UNKNOWN);
            Map<String, Object> details = Optional.ofNullable((Map<String, ?>) map.get(DETAIL)).map(this::extractUserDetails).orElseGet(HashMap::new);
            Collection<? extends GrantedAuthority> authorities = extractAuthorities(map);

            UserAuthentication userAuthentication = new UserAuthentication(Long.valueOf(principal.toString()), authorities, true);
            userAuthentication.setLoginType(loginType);
            userAuthentication.setDeviceType(deviceType);
            userAuthentication.setDetails(details);
            return userAuthentication;
        } else {
            // 以默认方式读取凭证数据
            return super.extractAuthentication(map);
        }
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
    protected Collection<? extends GrantedAuthority> extractAuthorities(Map<String, ?> map) {
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
        throw new IllegalArgumentException("authority list is incorrect");
    }

}
