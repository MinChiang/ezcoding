package com.test;

import com.ezcoding.common.foundation.core.enums.BooleanTypeEnum;
import com.ezcoding.common.foundation.core.message.MessageFactory;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.foundation.util.ResponseUtils;
import com.ezcoding.common.sdk.HttpUtils;
import org.junit.Test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-23 11:21
 */
public class HttpUtilsTest {


    @Test
    public void doPostRequest() {
        RequestMessage<?> requestMessage = MessageFactory.buildRequestMessage(1);
        ResponseMessage<Long> responseMessage = HttpUtils.doPostRequest("http://localhost:8081/oauth/test2", requestMessage);
        Long id = ResponseUtils.checkAndGet(responseMessage);
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
