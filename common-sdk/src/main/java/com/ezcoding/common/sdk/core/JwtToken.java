package com.ezcoding.common.sdk.core;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;
import com.ezcoding.common.foundation.core.enums.EnumMappableUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtHandlerAdapter;
import io.jsonwebtoken.Jwts;

import java.security.PublicKey;
import java.util.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-26 10:50
 */
public class JwtToken implements Token {

    public static final String PRINCIPAL = "principal";
    public static final String DETAIL = "detail";
    public static final String AUTHORITIES = "authorities";
    public static final String LOGIN_TYPE = "login_type";
    public static final String DEVICE_TYPE = "device_type";

    private final String token;

    private Date expireTime;
    private UserAuthentication userAuthentication;

    public JwtToken(PublicKey publicKey, String token) {
        this.token = token;
        init(publicKey);
    }

    @Override
    public boolean isTokenExpired() {
        return new Date().after(expireTime);
    }

    @Override
    public UserAuthentication acquireUserAuthentication() {
        return this.userAuthentication;
    }

    /**
     * 获取解析器
     */
    private void init(PublicKey publicKey) {
        Jwts
                .parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parse(this.token, new UserAuthenticationJwtHandler(this));
    }

    private static class UserAuthenticationJwtHandler extends JwtHandlerAdapter<UserAuthentication> {

        JwtToken jwtToken;

        public UserAuthenticationJwtHandler(JwtToken jwtToken) {
            this.jwtToken = jwtToken;
        }

        @Override
        public UserAuthentication onClaimsJws(Jws<Claims> jws) {
            Claims body = jws.getBody();

            Date expiration = body.getExpiration();

            Long principal = body.get(PRINCIPAL, Long.class);
            LoginRegisterTypeEnum loginType = Optional.ofNullable(body.get(LOGIN_TYPE, Integer.class)).map(type -> EnumMappableUtils.map(type, LoginRegisterTypeEnum.class)).orElse(LoginRegisterTypeEnum.UNKNOWN);
            DeviceTypeEnum deviceTypeEnum = Optional.ofNullable(body.get(DEVICE_TYPE, Integer.class)).map(type -> EnumMappableUtils.map(type, DeviceTypeEnum.class)).orElse(DeviceTypeEnum.UNKNOWN);

            List<String> authorities = body.get(AUTHORITIES, List.class);
            Map<String, Object> detail = body.get(DETAIL, Map.class);

            this.jwtToken.userAuthentication = new UserAuthentication(principal, loginType, deviceTypeEnum, authorities == null ? new ArrayList<>() : authorities, detail == null ? new HashMap<>() : detail);
            this.jwtToken.expireTime = expiration;

            return this.jwtToken.userAuthentication;
        }

    }

}
