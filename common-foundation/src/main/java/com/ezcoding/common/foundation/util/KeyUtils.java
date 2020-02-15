package com.ezcoding.common.foundation.util;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-05-29 11:06
 */
public class KeyUtils {

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
    private static byte[] readKey(String path) throws IOException {
        String content = loadKey(path);
        return Base64.getDecoder().decode(content);
    }

    /**
     * 读取文件中的私钥
     *
     * @param file 待读取的文件
     * @return 读取的私钥
     */
    public static PrivateKey loadPrivatekey(String file) throws IOException {
        byte[] privateContent = readKey(file);
        if (privateContent != null) {
            try {
                return KeyFactory
                        .getInstance("RSA")
                        .generatePrivate(new PKCS8EncodedKeySpec(privateContent));
            } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取文件中的公钥
     *
     * @param file 待读取的文件
     * @return 读取的公钥
     */
    public static PublicKey loadPublickey(String file) throws IOException {
        byte[] publicContent = readKey(file);
        if (publicContent != null) {
            try {
                return KeyFactory
                        .getInstance("RSA")
                        .generatePublic(new X509EncodedKeySpec(publicContent));
            } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取文件中的公钥
     *
     * @param inputStream 待读取的输入流
     * @return 读取的公钥
     */
    public static PublicKey loadPublickey(InputStream inputStream) {
        try {
            byte[] publicContent = IOUtils.toByteArray(inputStream);
            if (publicContent != null) {
                return KeyFactory
                        .getInstance("RSA")
                        .generatePublic(new X509EncodedKeySpec(publicContent));
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件中公钥或私钥内容
     *
     * @param file 待读取的文件
     * @return 读取的内容
     */
    public static String loadKey(String file) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(file);
        if (is == null) {
            throw new FileNotFoundException(file + "文件不存在");
        }
        List<String> lines = IOUtils.readLines(is, StandardCharsets.UTF_8);
        return String.join("", lines.subList(1, lines.size() - 1));
    }

}
