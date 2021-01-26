package com.ezcoding.common.sdk.util;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PublicKey;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-26 18:14
 */
public class KeyUtilsTest {

    @Test
    public void getPublicKey() throws Exception {
        File file = new File("D:\\workspace\\ezcoding_distribution\\common-sdk\\src\\test\\resources\\rsa_public_key.pem");
        PublicKey publicKey = KeyUtils.acquirePublicKey(file);
        System.out.println(publicKey.getFormat());


    }

}