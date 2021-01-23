package com.ezcoding.common.sdk;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-22 17:50
 */
public class HttpUtils {

    private static final OkHttpClient INSTANCE;
    private static final ObjectMapper OBJECT_MAPPER;
    private static final String TYPE = "application/json";
    private static final String CHARSET = "utf-8";
    public static final MediaType MEDIA_TYPE = MediaType.get(TYPE + ";charset=" + CHARSET);

    static {
        INSTANCE = new OkHttpClient();
        OBJECT_MAPPER = new ObjectMapper();
    }

    /**
     * 处理响应
     *
     * @param response 响应信息
     * @param <V>      响应泛型
     * @return 响应信息
     * @throws IOException io异常
     */
    private static <V> ResponseMessage<V> handleResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            throw new RuntimeException("error request to url: [" + response.request().url() + "] , response status: [" + response.code() + "] , message: [" + response.message() + "]");
        }
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            TypeReference<ResponseMessage<V>> responseMessageTypeReference = new TypeReference<ResponseMessage<V>>() {
            };
            return OBJECT_MAPPER.readValue(responseBody.bytes(), responseMessageTypeReference);
        }
        throw new RuntimeException("error response from url: [" + response.request().url() + "]");
    }

    /**
     * 开始请求
     *
     * @param request 请求体
     * @param <V>     响应泛型
     * @return 响应信息
     * @throws IOException io异常
     */
    private static <V> ResponseMessage<V> doRequest(Request request) throws IOException {
        Response response = INSTANCE.newCall(request).execute();
        return handleResponse(response);
    }

    /**
     * 开始post请求
     *
     * @param url            请求路径
     * @param requestMessage 请求信息
     * @param <K>            请求泛型
     * @param <V>            响应泛型
     * @return 响应信息
     */
    public static <K, V> ResponseMessage<V> doPostRequest(String url, RequestMessage<K> requestMessage) {
        try {
            String requestBody = OBJECT_MAPPER.writeValueAsString(Objects.requireNonNull(requestMessage));
            Request request = new Request
                    .Builder()
                    .get()
                    .post(RequestBody.create(MEDIA_TYPE, requestBody))
                    .url(url)
                    .build();
            return doRequest(request);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * 开始get请求
     *
     * @param url 请求路径
     * @param <V> 响应泛型
     * @return 响应信息
     */
    public static <V> ResponseMessage<V> doGetRequest(String url) {
        try {
            Request request = new Request
                    .Builder()
                    .get()
                    .url(url)
                    .build();
            return doRequest(request);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * 开始请求
     *
     * @param request        请求体
     * @param <V>            响应泛型
     * @param callbackAction 回调函数
     */
    private static <V> void doRequestAsync(Request request, CallbackAction<V> callbackAction) {
        INSTANCE.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                throw new RuntimeException(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callbackAction.callback(handleResponse(response));
            }
        });
    }

    /**
     * 异步post请求
     *
     * @param url            请求路径
     * @param requestMessage 请求信息
     * @param callbackAction 回调函数
     * @param <K>            请求泛型
     * @param <V>            响应泛型
     */
    public static <K, V> void doPostRequestAsync(String url, RequestMessage<K> requestMessage, CallbackAction<V> callbackAction) {
        try {
            String requestBody = OBJECT_MAPPER.writeValueAsString(Objects.requireNonNull(requestMessage));
            Request request = new Request
                    .Builder()
                    .get()
                    .post(RequestBody.create(MEDIA_TYPE, requestBody))
                    .url(url)
                    .build();
            doRequestAsync(request, callbackAction);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * 异步get请求
     *
     * @param url            请求路径
     * @param callbackAction 回调函数
     * @param <V>            响应泛型
     */
    public static <V> void doGetRequestAsync(String url, CallbackAction<V> callbackAction) {
        Request request = new Request
                .Builder()
                .url(url)
                .build();
        doRequestAsync(request, callbackAction);
    }

    public interface CallbackAction<V> {

        /**
         * 成功回调
         *
         * @param responseMessage 响应内容
         */
        void callback(ResponseMessage<V> responseMessage);

    }

}
