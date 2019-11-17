package com.ezcoding.sdk.auth.resource.api;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-20 17:36
 */
@FeignClient(name = "${service.auth:127.0.0.1}")
@RequestMapping("resource")
public interface AuthFeignClient {

    /**
     * 根据code查找对应的用户
     *
     * @param requestMessage 请求信息
     * @return 用户信息
     */
    @PostMapping("hasResource")
    ResponseMessage<Boolean> hasResource(RequestMessage requestMessage);

}
