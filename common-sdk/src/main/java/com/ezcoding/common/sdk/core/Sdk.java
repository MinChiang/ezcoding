package com.ezcoding.common.sdk.core;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.foundation.core.message.MessageFactory;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.util.ResponseUtils;
import com.ezcoding.common.sdk.util.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-29 15:57
 */
public class Sdk implements TokenStorable {

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder().build();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
    private String completeUrl(String relativeUrl) {
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

    /**
     * 根据账号密码登录
     *
     * @param account    账号
     * @param password   密码
     * @param deviceType 设备类型
     * @return token
     */
    public Token loginByAccountPassword(String account, String password, DeviceTypeEnum deviceType) throws IOException {
        if (account == null || account.isEmpty()) {
            throw new IllegalArgumentException("account and not be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password and not be empty");
        }
        UserLoginRequestDTO userLoginRequestDTO = UserLoginRequestDTO.createByAccountPassword(account, password, deviceType);
        RequestMessage<UserLoginRequestDTO> requestMessage = MessageFactory.buildRequestMessage(userLoginRequestDTO);
        Request request = HttpUtils.handleRequest(completeUrl(UrlConstants.OAUTH_AUTHORIZE), HttpUtils.METHOD_POST, null, null, requestMessage);
        Response response = OK_HTTP_CLIENT.newCall(request).execute();
        String location = response.header("Location");
        ResponseMessage<?> responseMessage = HttpUtils.handleResponse(response);
        ResponseUtils.check(responseMessage);
        return null;
    }

    /**
     * 根据手机号密码登录
     *
     * @param phone    手机号
     * @param password 密码
     * @return token
     */
    public Token loginByPhonePassword(String phone, String password) {

        return null;
    }

}
