package com.ezcoding.common.sdk.core;

import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.util.ResponseUtils;
import com.ezcoding.common.sdk.util.HttpUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-29 15:57
 */
public class Sdk {

    private SdkConfig sdkConfig;
    private volatile Token token;
    private volatile String publicKey;

    Sdk(SdkConfig sdkConfig) {
        this.sdkConfig = sdkConfig;
        //获取公钥
        synchronized (this) {
            ResponseMessage<String> responseMessage = HttpUtils.doGetRequest(completeUrl(UrlConstants.OAUTH_PUBLIC_KEY), null);
            this.publicKey = ResponseUtils.checkAndGet(responseMessage);
            System.out.println(this.publicKey);
        }
    }

    public SdkConfig getSdkConfig() {
        return sdkConfig;
    }

    /**
     * 获取完整的url路径
     *
     * @param relativeUrl 相对url路径
     * @return 完整路径
     */
    public String completeUrl(String relativeUrl) {
        return UrlConstants.formatUrl(this.sdkConfig.getBaseUrl(), relativeUrl);
    }

}
