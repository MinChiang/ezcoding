package com.ezcoding.common.sdk.core;

import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.util.ResponseUtils;
import com.ezcoding.common.sdk.util.HttpUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-29 15:57
 */
public class Sdk implements TokenStorable {

    private final SdkConfig sdkConfig;

    private transient volatile Token token;
    private transient volatile String publicKey;

    Sdk(SdkConfig sdkConfig) {
        this.sdkConfig = sdkConfig;
        this.init();
    }

    /**
     * 初始化
     */
    private void init() {
        synchronized (this) {
            //获取公钥
            ResponseMessage<String> responseMessage = HttpUtils.doGetRequest(completeUrl(UrlConstants.OAUTH_PUBLIC_KEY), null, null);
            this.publicKey = ResponseUtils.checkAndGet(responseMessage);
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
        return this.sdkConfig.getBaseUrl() + relativeUrl;
    }

    @Override
    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public Token getToken() {
        return this.token;
    }

    public Token login() {
        return null;
    }

}
