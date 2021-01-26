package com.ezcoding.common.sdk.core;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-26 10:50
 */
public class TokenHelper implements TokenParsable {

    public static final String PRINCIPAL = "principal";
    public static final String DETAIL = "detail";
    public static final String AUTHORITIES = "authorities";

    private final PublicKey publicKey;
    private final String rawToken;

    private Date expireTime;
    private UserAuthentication userAuthentication;

    public TokenHelper(PublicKey publicKey, String rawToken) {
        this.publicKey = publicKey;
        this.rawToken = rawToken;
        init();

    }

    @Override
    public boolean isTokenExpired() {
        return false;
    }

    @Override
    public UserAuthentication acquireUserAuthentication() {
        return this.userAuthentication;
    }

    /**
     * 获取解析器
     */
    private void init() {
        this.userAuthentication = Jwts
                .parserBuilder()
                .setSigningKey(this.publicKey)
                .build()
                .parse(this.rawToken, new UserAuthenticationJwtHandler());
    }

    private static class UserAuthenticationJwtHandler extends JwtHandlerAdapter<UserAuthentication> {

        @Override
        public UserAuthentication onClaimsJws(Jws<Claims> jws) {
            Claims body = jws.getBody();
            Long principal = body.get(PRINCIPAL, Long.class);
            List<String> list = body.get(AUTHORITIES, List.class);
            Map<String, Object> map = body.get(DETAIL, Map.class);

            return new UserAuthentication(principal, null, null, Collections.emptyList(), new HashMap<>());

        }

    }

}
