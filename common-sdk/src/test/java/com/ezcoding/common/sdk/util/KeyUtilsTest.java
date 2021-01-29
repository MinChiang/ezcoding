package com.ezcoding.common.sdk.util;

import com.ezcoding.common.foundation.util.PublicKeyUtils;
import org.junit.Test;

import java.io.File;
import java.security.PublicKey;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-26 18:14
 */
public class KeyUtilsTest {

    @Test
    public void getPublicKey() throws Exception {
        File file = new File("D:\\workspace\\ezcoding_distribution\\common-sdk\\src\\test\\resources\\jwtRS256.key.pub");
        PublicKey publicKey = PublicKeyUtils.acquirePublicKey(file);
        System.out.println(publicKey.getFormat());


    }

}