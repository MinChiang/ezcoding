package com.test;

import com.ezcoding.common.foundation.core.message.MessageFactory;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.util.ResponseUtils;
import com.ezcoding.common.sdk.util.HttpUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-23 11:21
 */
public class HttpUtilsTest {

    @Test
    public void doPostRequest() {
        RequestMessage<?> requestMessage = MessageFactory.buildRequestMessage(1);
        Map<String, String> map = new HashMap<>();
        map.put("path", "public_key");
        ResponseMessage<String> responseMessage = HttpUtils.doPostRequest("http://localhost:8089/oauth/{path}", map, null, requestMessage);
        String id = ResponseUtils.checkAndGet(responseMessage);
        System.out.println(id);
    }

    @Test
    public void doGetRequest() {
    }

    @Test
    public void doPostRequestAsync() {
    }

    @Test
    public void doGetRequestAsync() {
    }

}
