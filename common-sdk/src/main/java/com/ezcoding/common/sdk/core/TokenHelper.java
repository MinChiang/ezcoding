package com.ezcoding.common.sdk.core;

import io.jsonwebtoken.*;

import java.security.PublicKey;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-26 10:50
 */
public class TokenHelper implements TokenParsable {

    private PublicKey publicKey;
    private JwtParser jwtParser;

    public TokenHelper(PublicKey publicKey) {
        this.publicKey = publicKey;
        buildParser();
    }

    @Override
    public boolean isTokenExpired(String token) {
//        return this.jwtParser.parse(token).getBody().
        return false;
    }

    @Override
    public UserAuthentication parseToken(String token) {
//        jwtParser.parse(token, )
        return null;
    }

    /**
     * 获取解析器
     */
    private void buildParser() {
        this.jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(this.publicKey)
                .build();
    }

    private static class UserAuthenticationJwtHandler extends JwtHandlerAdapter<UserAuthentication> {

        @Override
        public UserAuthentication onClaimsJws(Jws<Claims> jws) {
//            Claims body = jws.getBody();
//            body.get()
            return null;
        }

    }

}
