package com.ezcoding.facility.module.message.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.facility.module.message.bean.model.Message;
import com.ezcoding.facility.module.message.bean.model.MessageReadRecord;
import com.ezcoding.facility.module.message.bean.model.MessageSearchCondition;
import com.ezcoding.facility.module.message.bean.model.Subscription;
import com.ezcoding.facility.module.message.dao.MessageMapper;
import com.ezcoding.facility.module.message.dao.SubscriptionMapper;
import com.ezcoding.facility.module.message.service.IMessageReadRecordService;
import com.ezcoding.facility.module.message.service.IMessageService;
import com.ezcoding.sdk.facility.message.bean.dto.MessageSearchDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-19 10:54
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private IMessageReadRecordService messageReadRecordService;

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @Override
    public Subscription subscribe(Subscription subscription) {
        Subscription tmp = new Subscription();
        tmp.setSubscriber(subscription.getSubscriber());
        tmp.setScene(subscription.getScene());
        tmp.setSubjectId(subscription.getSubjectId());

        //先查询用户是否已经进行了订阅操作
        tmp = subscriptionMapper.selectOne(Wrappers.query(tmp));
        if (tmp != null) {
            return tmp;
        }
        subscriptionMapper.insert(subscription);
        return subscription;
    }

    @Override
    public void unsubscribe(Subscription subscription) {
        AssertUtils.mustNotEmpty(subscription.getSubscriber(), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("订阅人").build());
        AssertUtils.mustFalse(subscription.getId() == null &&
                        (StringUtils.isEmpty(subscription.getScene()) || subscription.getSubjectId() == null || StringUtils.isEmpty(subscription.getApplicationCode())),
                CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("订阅id或订阅场景号、订阅物品主键").build());
        subscriptionMapper.delete(Wrappers.query(subscription));
    }

    @Override
    public Message record(Message message) {
        messageMapper.insert(message);
        return message;
    }

    @Override
    public Integer countUnread(MessageSearchCondition condition) {
        Integer count = messageMapper.selectUnreadCountByCondition(condition);
        return count == null ? 0 : count;
    }

    @Override
    public void read(String user, Collection<Long> messages) {
        if (user == null || CollectionUtils.isEmpty(messages)) {
            return;
        }
        //搜索存在并且未读的消息
        List<Long> ids = messageMapper.selectExistAndUnread(user, messages);
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        Date date = new Date();
        //插入已读消息表
        List<MessageReadRecord> collect = ids.stream().map(i -> {
            MessageReadRecord messageReadRecord = new MessageReadRecord();
            messageReadRecord.setMessageId(i);
            messageReadRecord.setUser(user);
            messageReadRecord.setReadTime(date);
            return messageReadRecord;
        }).collect(Collectors.toList());
        messageReadRecordService.saveBatch(collect);
    }

    @Override
    public Page<MessageSearchDTO> fetch(MessageSearchCondition condition, Page<MessageSearchDTO> page) {
        List<MessageSearchDTO> messageSearchDTOs = messageMapper.selectListByCondition(condition, page);
        page.setRecords(messageSearchDTOs);
        return page;
    }

}
