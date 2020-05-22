package com.ezcoding.test;

import com.ezcoding.common.foundation.core.message.StandardResponseHttpEntity;
import com.ezcoding.common.foundation.core.message.StandardResponseMessageBuilder;
import org.junit.jupiter.api.Test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-05-22 11:25
 */
public class ResponseHttpEntityTest {

    @Test
    public void test() {
        StandardResponseHttpEntity<Object> build = StandardResponseMessageBuilder.ok().success("123").build();
        System.out.println(build);
    }

}
