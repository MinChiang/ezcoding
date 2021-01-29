package com.ezcoding.common.foundation.util;

import sun.misc.BASE64Decoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-26 18:12
 */
public class PublicKeyUtils {

    public static final String BEGIN_KEY = "-----BEGIN PUBLIC KEY-----\r\n";
    public static final String END_KEY = "\r\n-----END PUBLIC KEY-----";

    /**
     * 去除原生公钥的首位注解
     *
     * @param rawKey 原生公钥
     * @return 处理后的公钥
     */
    public static String removePrefixAndSuffix(String rawKey) {
        rawKey = rawKey.trim();
        if (rawKey.startsWith(BEGIN_KEY)) {
            rawKey = rawKey.substring(BEGIN_KEY.length());
        }
        if (rawKey.endsWith(END_KEY)) {
            rawKey = rawKey.substring(0, rawKey.length() - END_KEY.length());
        }
        return rawKey;
    }

    /**
     * 获取公钥
     *
     * @param key 公钥内容
     * @return 公钥
     * @throws Exception 异常
     */
    private static PublicKey acquirePublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param file 文件
     * @return 公钥
     * @throws Exception 异常
     */
    public static String acquireRawPublicKey(File file) throws Exception {
        InputStream fileInputStream = new FileInputStream(file);
        int len;
        byte[] bytes = new byte[128];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = fileInputStream.read(bytes)) != -1) {
            baos.write(bytes, 0, len);
        }
        return baos.toString();
    }

    /**
     * 获取公钥
     *
     * @param file 公钥文件
     * @return 公钥
     * @throws Exception 异常
     */
    public static PublicKey acquirePublicKey(File file) throws Exception {
        String key = acquireRawPublicKey(file);
        key = removePrefixAndSuffix(key);
        return acquirePublicKey(key);
    }

}
