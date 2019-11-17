package com.ezcoding.sdk.facility.message.api;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.sdk.facility.message.bean.dto.MessageDTO;
import com.ezcoding.sdk.facility.message.bean.dto.SubscriptionDTO;
import com.ezcoding.sdk.facility.message.constant.FacilityMessageApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-17 22:16
 */
@FeignClient(name = "${service.facility:127.0.0.1}")
@RequestMapping(FacilityMessageApiConstants.MESSAGE_API)
public interface MessageFeignClient {

    /**
     * 记录信息
     *
     * @param requestMessage 请求信息
     * @return 记录信息
     */
    @PostMapping(FacilityMessageApiConstants.RECORD)
    ResponseMessage record(RequestMessage<MessageDTO> requestMessage);

    /**
     * 订阅消息
     *
     * @param requestMessage 请求信息
     * @return 用户信息
     */
    @PostMapping(FacilityMessageApiConstants.SUBSCRIBE)
    ResponseMessage subscribe(RequestMessage<SubscriptionDTO> requestMessage);

    /**
     * 取消订阅
     *
     * @param requestMessage 请求信息
     * @return 用户信息
     */
    @PostMapping(FacilityMessageApiConstants.UNSUBSCRIBE)
    ResponseMessage unsubscribe(RequestMessage<SubscriptionDTO> requestMessage);

}
