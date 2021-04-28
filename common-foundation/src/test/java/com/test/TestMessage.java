package com.test;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.MessageFactory;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.tools.uuid.SnowflakeIdProducer;
import org.junit.Test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-04-28 17:03
 */
public class TestMessage {

    @Test
    public void testMessageFactory() {
        MessageFactory.setSequenceNoProducer(new SnowflakeIdProducer(1L, 1L));
        ResponseMessage<String> hello = MessageFactory.buildSuccessResponseMessage("hello");
        System.out.println(hello.toString());

        ResponseMessage<String> stringResponseMessage = MessageFactory.buildErrorResponseMessage(new ApplicationException("123", "hahah", new Throwable("1")), "我的");
        System.out.println(stringResponseMessage);

    }

}
