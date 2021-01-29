package com.ezcoding.common.sdk.core;

import com.ezcoding.common.foundation.util.PublicKeyUtils;
import org.junit.Test;

import java.io.File;
import java.security.PublicKey;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-26 17:49
 */
public class TokenHelperTest {

    private static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcmluY2lwYWwiOjEsInNjb3BlIjpbImFwcCJdLCJkZXRhaWwiOnsibG9naW5fdHlwZSI6IkFDQ09VTlRfUEFTU1dPUkQiLCJkZXZpY2VfdHlwZSI6IlVOS05PV04ifSwiZXhwIjoxNjEyMzcwMzA2LCJpYXQiOjE2MTE3NjU1MDYsImF1dGhvcml0aWVzIjpbIlJPTEVfREVWRUxPUEVSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiIxYzIzN2Q3OS04ZTkyLTRiMGItYjRjMy1jZTdhZWQzOTNhOGYiLCJjbGllbnRfaWQiOiJ0ZXN0Q2xpZW50In0.RKqdCDfaZRy6bMBmS4b22NjsHUVla4m0WqLMLq8RKA8VueDfp_sVkazF2xqlNdvcK-vmqvZ50Ajw21XN752hF9SyoLv_9WeKRu4iDq2UTyw3DnjZNy1pABR9F7nw_10rOtI45kE3zbHizHgu6JUA4UnnbLSy1k01C272wm8OFqfnUgYLjqnRwA_Ccq0OLblKvQtw6pScZtTGbUMRXqleJAGbGxk4V049_EaL913j_tND7UCSQfiZD_cWro5N_tQdAnBm8c2X8ZSEI0jeXf8JTw918AhitE32XVDeRAHv-eyVEqF2IMBXgtKzYP0vVtp2TPlOdM6J0GSZ9l8nvuHYCGsUw6JgOb8H1pe7g4oTcB_DmCR6ScQ5s80V9Mgku4UERx7kUFOoMyTglfvcKn5ttnAEK-4uEeqXtV6aKs91wHE6pso2-c1u29VTkRbdP6KlPRR_kpQ5HAik4HVHxlX5NEeNUN9nz5p-xWllntxJdbkmv7fEAcCnQBlolR5FzsCdZA-Ww7mxzT93FhDuqC-2qdXSrRye5FNWU-ueRugcQuM4-aoknMTmMOabE6KldGrBLR3ncCa7tB3VdwJqLXQ5KGBoWq28VqrG4BwNDCLJyXU2KWrdxmPJ9-HsscyYo8aoeG6HroZFGjzv1hL7ysZrFc1IRfOu0HOsLCXStKkvi48";

    @Test
    public void acquireUserAuthentication() throws Exception {
        File file = new File("D:\\workspace\\ezcoding_distribution\\common-sdk\\src\\test\\resources\\jwtRS256.key.pub");
        PublicKey publicKey = PublicKeyUtils.acquirePublicKey(file);
        System.out.println(publicKey.getFormat());

        JwtToken jwtToken = new JwtToken(publicKey, TOKEN);
        UserAuthentication userAuthentication = jwtToken.acquireUserAuthentication();
    }

}