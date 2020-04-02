package com.ezcoding.common.web.jwt;

import com.ezcoding.common.foundation.core.tools.uuid.IUUIDProducer;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUUIDProducer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-05 23:23
 */
public class TokenTools {

    public static final Long EXPIRATION_DEFAULT = AuthSettings.DEFAULT_EXPIRATION;
    public static final String SUBJECT_DEFAULT = "subject";
    public static final String ISSUER_DEFAULT = "issuer";
    public static final String SECRET_DEFAULT = "secret";

    private Long expiration = EXPIRATION_DEFAULT;
    private String subject = SUBJECT_DEFAULT;
    private String issuer = ISSUER_DEFAULT;
    private String secret = SECRET_DEFAULT;

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private IUUIDProducer idProducer = OriginalUUIDProducer.getInstance();

    public TokenTools(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    /**
     * 加载私钥和密钥
     *
     * @param path 文件路径
     * @return 文件内容，已经去除私钥和公钥中的
     * -----BEGIN PRIVATE KEY-----
     * -----END PRIVATE KEY----
     * -----BEGIN PUBLIC KEY-----
     * -----END PUBLIC KEY-----
     */
    private static byte[] readKey(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            List<String> lines = FileUtils.readLines(resource.getFile(), "UTF-8");
            String collect = String.join("", lines.subList(1, lines.size() - 1));
            return Base64.getDecoder().decode(collect);
        } catch (IOException ignored) {

        }
        return null;
    }

    /**
     * 生成令牌
     *
     * @param otherDetail 令牌内容信息
     * @return 令牌
     */
    public String generateToken(Map<String, Object> otherDetail) {
        DefaultClaims defaultClaims = new DefaultClaims(otherDetail);
        return generateToken(defaultClaims);
    }

    /**
     * 生成令牌
     *
     * @param claims 令牌内容信息
     * @return 令牌
     */
    public String generateToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setId(idProducer.produce())
                .setSubject(subject)
                .setIssuer(issuer)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 生成token
     *
     * @param name 附加信息的名称
     * @param obj  附加的信息
     * @return token
     */
    public String generateToken(String name, Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put(name, obj);
        return generateToken(map);
    }

    /**
     * 刷新令牌
     *
     * @param token 旧的令牌
     * @return 新的令牌
     */
    public String refreshToke(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Claims claims = parseToken(token);
        return generateToken(claims);
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 令牌是否过期
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 尝试解析令牌
     *
     * @param token 令牌
     * @return 令牌有效负载数据
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 尝试解析令牌
     *
     * @param token 令牌
     * @return 令牌有效负载数据
     */
    public Map<String, Object> parseTokenToMap(String token) {
        Claims claims = parseToken(token);
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * 尝试解析令牌
     *
     * @param token 令牌
     * @param name  设置的名称
     * @param type  需要返回的类型
     * @return 令牌有效负载数据
     */
    public <T> T parseTokenToObject(String token, String name, Class<T> type) {
        Claims claims = parseToken(token);
        return claims.get(name, type);
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public IUUIDProducer getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(IUUIDProducer idProducer) {
        this.idProducer = idProducer;
    }

}