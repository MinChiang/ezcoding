package com.ezcoding.facility.module.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ezcoding.facility.module.message.bean.model.Message;
import com.ezcoding.facility.module.message.bean.model.MessageSearchCondition;
import com.ezcoding.facility.module.message.bean.model.Subscription;
import com.ezcoding.sdk.facility.message.bean.dto.MessageSearchDTO;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-19 9:55
 */
public interface IMessageService extends IService<Message> {

    /**
     * 用户订阅
     *
     * @param subscription 订阅关系
     * @return 订阅关系
     */
    Subscription subscribe(Subscription subscription);

    /**
     * 取消订阅
     *
     * @param subscription 订阅关系
     */
    void unsubscribe(Subscription subscription);

    /**
     * 记录消息
     *
     * @param message 动作
     * @return 插入的消息
     */
    Message record(Message message);

    /**
     * 检索未读消息的数量
     *
     * @param condition 检索条件
     * @return 未读消息的数量
     */
    Integer countUnread(MessageSearchCondition condition);

    /**
     * 读取消息
     *
     * @param user     读取消息的用户
     * @param messages 读取的消息id列表
     */
    void read(String user, Collection<Long> messages);

    /**
     * 根据条件检索消息
     *
     * @param condition 检索条件
     * @param page      分页
     * @return 消息
     */
    Page<MessageSearchDTO> fetch(MessageSearchCondition condition, Page<MessageSearchDTO> page);

}
