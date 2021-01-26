package com.ezcoding.common.sdk.core;

import com.ezcoding.common.sdk.util.KeyUtils;
import org.junit.Test;

import java.io.File;
import java.security.PublicKey;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-26 17:49
 */
public class TokenHelperTest {

    private static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcmluY2lwYWwiOjEsInNjb3BlIjpbImFwcCJdLCJkZXRhaWwiOnsibG9naW5fdHlwZSI6IkFDQ09VTlRfUEFTU1dPUkQiLCJkZXZpY2VfdHlwZSI6IlVOS05PV04ifSwiZXhwIjoxNjEyMjU2NDYwLCJpYXQiOjE2MTE2NTE2NjAsImF1dGhvcml0aWVzIjpbIlJPTEVfREVWRUxPUEVSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiJhZDVkNGRhYi1jMWQ4LTQzZTItOWE5YS0xMGVhOWNmOGE2ZTQiLCJjbGllbnRfaWQiOiJ0ZXN0Q2xpZW50In0.mVLeXI-kqxVHu8C6c5Ca2adpprz4K7QSfKhFyAQlb5ItjxLZf-17PjiiaBAoOvWx-Ig0v-b-YEgOLu3vx8LyLIsR7hvuOj8y2tB-9CzEE8LL0hqk_mKlqBOuhDHSRPHssQ3v_5vj--wQ551N-iKYlbKgavAAgZCWykNeWB0SsEY";

    @Test
    public void acquireUserAuthentication() throws Exception {
        File file = new File("D:\\workspace\\ezcoding_distribution\\common-sdk\\src\\test\\resources\\rsa_public_key.pem");
        PublicKey publicKey = KeyUtils.acquirePublicKey(file);
        System.out.println(publicKey.getFormat());

        TokenHelper tokenHelper = new TokenHelper(publicKey, TOKEN);
        UserAuthentication userAuthentication = tokenHelper.acquireUserAuthentication();
    }

}