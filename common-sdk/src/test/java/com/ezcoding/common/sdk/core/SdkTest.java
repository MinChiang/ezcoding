package com.ezcoding.common.sdk.core;

import com.ezcoding.common.foundation.core.enviroment.Environment;
import org.junit.Test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-29 23:57
 */
public class SdkTest {

    @Test
    public void sdkTest() {
        Sdk sdk = SdkFactory.create(Environment.LOCAL);
    }

}
