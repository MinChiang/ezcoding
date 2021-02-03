package com.ezcoding.common.foundation.util;

import sun.misc.BASE64Decoder;

import java.io.*;
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
    public static String removeComment(String rawKey) {
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
     */
    private static PublicKey convertPublicKey(String key) {
        PublicKey publicKey;
        try {
            byte[] keyBytes;
            keyBytes = (new BASE64Decoder()).decodeBuffer(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return publicKey;
    }

    /**
     * 根据流读取公钥
     *
     * @param inputStream 流
     * @return 公钥内容
     */
    private static String readKey(InputStream inputStream) {
        ByteArrayOutputStream baos;
        try {
            int len;
            byte[] bytes = new byte[128];
            baos = new ByteArrayOutputStream();
            while ((len = inputStream.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baos.toString();
    }

    /**
     * 获取公钥
     * 公钥中含有对应的头部尾部注释
     * -----BEGIN PUBLIC KEY-----
     * -----END PUBLIC KEY-----
     *
     * @param file 文件
     * @return 公钥
     */
    public static String acquireRawPublicKey(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        String result = null;
        try {
            result = readKey(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(result);
        }
        return result;
    }

    /**
     * 获取公钥
     * 公钥中含有对应的头部尾部注释
     * -----BEGIN PUBLIC KEY-----
     * -----END PUBLIC KEY-----
     *
     * @param path 公钥文件路径
     * @return 公钥
     */
    public static String acquireRawPublicKey(String path) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            return null;
        }
        return readKey(inputStream);
    }

    /**
     * 获取公钥
     * 公钥中含无对应的头部尾部注释
     *
     * @param file 公钥文件
     * @return 公钥
     */
    public static PublicKey acquirePublicKey(File file) {
        String key = acquireRawPublicKey(file);
        key = removeComment(key);
        return convertPublicKey(key);
    }

    /**
     * 获取公钥
     * 公钥中含无对应的头部尾部注释
     *
     * @param path 公钥文件路径
     * @return 公钥
     */
    public static PublicKey acquirePublicKey(String path) {
        String key = acquireRawPublicKey(path);
        key = removeComment(key);
        return acquirePublicKey(key);
    }

}
