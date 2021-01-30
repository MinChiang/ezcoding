package com.ezcoding.common.sdk.util;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-22 17:50
 */
public class HttpUtils {

    private static final OkHttpClient INSTANCE;
    private static final ObjectMapper OBJECT_MAPPER;
    private static final String TYPE_JSON = "application/json";
    private static final String CHARSET_UTF8 = "utf-8";
    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.get(TYPE_JSON + ";charset=" + CHARSET_UTF8);

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_PATCH = "PATCH";

    private static final String AUTHORIZATION = "Authorization";

    private static final String LEFT_BRACE = "{";
    private static final String RIGHT_BRACE = "}";

    static {
        INSTANCE = new OkHttpClient.Builder().build();
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
     * 处理请求
     *
     * @param method         请求方法
     * @param url            请求路径
     * @param urlParameters  url路径参数
     * @param parameters     路径参数
     * @param requestMessage 请求信息
     * @param <K>            请求类型
     * @return 请求
     * @throws IOException io异常
     */
    private static <K> Request handleRequest(String url, String method, Map<String, String> urlParameters, Map<String, String> parameters, RequestMessage<K> requestMessage) throws IOException {
        String requestBody = null;
        if (requestMessage != null) {
            requestBody = OBJECT_MAPPER.writeValueAsString(requestMessage);
        }

        if (urlParameters != null && !urlParameters.isEmpty()) {
            for (Map.Entry<String, String> entry : urlParameters.entrySet()) {
                url = url.replace(LEFT_BRACE + entry.getKey() + RIGHT_BRACE, entry.getValue());
            }
        }
        HttpUrl.Builder httpUrlBuilder = HttpUrl.get(url).newBuilder();
        if (parameters != null && !parameters.isEmpty()) {
            parameters.forEach(httpUrlBuilder::addQueryParameter);
        }

        Request.Builder requestBuilder = new Request.Builder().url(httpUrlBuilder.build());
        if (requestBody != null) {
            requestBuilder.method(method, RequestBody.create(DEFAULT_MEDIA_TYPE, requestBody));
        }
        return requestBuilder.build();
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
     * @param urlParameters  url路径参数
     * @param parameters     请求路径参数
     * @param requestMessage 请求信息
     * @param <K>            请求泛型
     * @param <V>            响应泛型
     * @return 响应信息
     */
    public static <K, V> ResponseMessage<V> doPostRequest(String url, Map<String, String> urlParameters, Map<String, String> parameters, RequestMessage<K> requestMessage) {
        try {
            return doRequest(handleRequest(url, METHOD_POST, urlParameters, parameters, requestMessage));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * 开始get请求
     *
     * @param url           请求路径
     * @param urlParameters url路径参数
     * @param parameters    请求路径参数
     * @param <V>           响应泛型
     * @return 响应信息
     */
    public static <V> ResponseMessage<V> doGetRequest(String url, Map<String, String> urlParameters, Map<String, String> parameters) {
        try {
            return doRequest(handleRequest(url, METHOD_GET, urlParameters, parameters, null));
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
                    .post(RequestBody.create(DEFAULT_MEDIA_TYPE, requestBody))
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
