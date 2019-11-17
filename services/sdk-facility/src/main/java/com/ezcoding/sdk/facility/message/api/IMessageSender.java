package com.ezcoding.sdk.facility.message.api;

import com.ezcoding.sdk.facility.message.bean.dto.MessageDTO;
import com.ezcoding.sdk.facility.message.bean.dto.SubscriptionDTO;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-09 20:09
 */
public interface IMessageSender {

    /**
     * 订阅消息
     *
     * @param subscription 订阅内容
     * @throws IllegalArgumentException 参数不正确
     */
    void subscribe(SubscriptionDTO subscription) throws IllegalArgumentException;

    /**
     * 取消订阅
     *
     * @param subscription 订阅内容
     * @throws IllegalArgumentException 参数不正确
     */
    void unsubscribe(SubscriptionDTO subscription) throws IllegalArgumentException;

    /**
     * 发送动作消息
     *
     * @param message 添加的信息
     * @throws IllegalArgumentException 参数不正确
     */
    void sendMessage(MessageDTO message) throws IllegalArgumentException;

}
