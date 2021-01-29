package com.ezcoding.common.sdk.core;

import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.sdk.util.HttpUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-29 15:57
 */
public class Sdk {

    private volatile Token token;
    private String publicKey;
    private String baseUrl;

    Sdk() {
        synchronized (Sdk.class) {
            ResponseMessage<Object> objectResponseMessage = HttpUtils.doGetRequest();
        }
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public static UserAuthentication acquireUserAuthentication() {
        if (token == null) {

        }
    }

}
