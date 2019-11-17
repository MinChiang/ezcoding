package com.ezcoding.facility.module.message.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.base.web.extend.spring.resolver.CurrentUser;
import com.ezcoding.base.web.extend.spring.resolver.JsonPage;
import com.ezcoding.base.web.extend.spring.resolver.JsonParam;
import com.ezcoding.base.web.extend.spring.resolver.JsonResult;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.facility.module.message.bean.model.Message;
import com.ezcoding.facility.module.message.bean.model.MessageSearchCondition;
import com.ezcoding.facility.module.message.bean.model.Subscription;
import com.ezcoding.facility.module.message.service.IMessageService;
import com.ezcoding.sdk.account.user.api.IUser;
import com.ezcoding.sdk.facility.message.bean.dto.MessageDTO;
import com.ezcoding.sdk.facility.message.bean.dto.MessageSearchDTO;
import com.ezcoding.sdk.facility.message.bean.dto.SubscriptionDTO;
import com.ezcoding.sdk.facility.message.constant.FacilityMessageApiConstants;
import com.ezcoding.sdk.facility.message.constant.FacilityMessageValidationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-20 14:49
 */
@Validated
@RestController
@RequestMapping(FacilityMessageApiConstants.MESSAGE_API)
public class MessageController {

    @Autowired
    private IMessageService messageService;

    /**
     * 订阅
     *
     * @param subscriptionDTO 订阅信息
     * @param user            订阅人
     * @return 订阅信息
     */
    @PostMapping(FacilityMessageApiConstants.SUBSCRIBE)
    @JsonResult
    public Subscription subscribe(@JsonParam @Validated(FacilityMessageValidationConstants.Subscribe.class) SubscriptionDTO subscriptionDTO,
                                  @CurrentUser IUser user) {
        Subscription subscription = BeanUtils.copy(subscriptionDTO, Subscription.class);
        subscription.setSubscriber(user.getCode());
        return messageService.subscribe(subscription);
    }

    /**
     * 取消订阅
     *
     * @param subscriptionDTO 订阅信息
     * @param user            订阅人
     */
    @PostMapping(FacilityMessageApiConstants.UNSUBSCRIBE)
    @JsonResult
    public void unsubscribe(@JsonParam SubscriptionDTO subscriptionDTO,
                            @CurrentUser IUser user) {
        Subscription subscription = BeanUtils.copy(subscriptionDTO, Subscription.class);
        subscription.setSubscriber(user.getCode());
        messageService.unsubscribe(subscription);
    }

    /**
     * 记录发生的动作信息
     *
     * @param messageDTO 新增的信息
     * @return 用户消息分页
     */
    @PostMapping(FacilityMessageApiConstants.RECORD)
    @JsonResult
    public Message record(@JsonParam @Validated(value = FacilityMessageValidationConstants.Record.class) MessageDTO messageDTO,
                          @CurrentUser IUser user) {
        Message message = BeanUtils.copy(messageDTO, Message.class);
        message.setCreateTime(new Date());
        message.setCreator(user.getCode());
        return messageService.record(message);
    }

    /**
     * 获取未读消息数量
     *
     * @param messageSearchCondition 筛选条件
     * @param user                   当前登录用户
     * @return 用户未读信息数量
     */
    @PostMapping(FacilityMessageApiConstants.COUNT_UNREAD)
    @JsonResult
    public Integer countUnread(@JsonParam MessageSearchCondition messageSearchCondition,
                               @CurrentUser IUser user) {
        if (messageSearchCondition == null) {
            messageSearchCondition = new MessageSearchCondition();
        }
        messageSearchCondition.setUser(user.getCode());
        return messageService.countUnread(messageSearchCondition);
    }

    /**
     * 读取消息
     *
     * @param messageIds 消息id
     * @param user       当前登录用户
     */
    @PostMapping(FacilityMessageApiConstants.READ)
    @JsonResult
    public void read(@JsonParam Set<Long> messageIds,
                     @CurrentUser IUser user) {
        messageService.read(user.getCode(), messageIds);
    }

    /**
     * 获取用户的消息
     *
     * @param messageSearchCondition 筛选条件
     * @param user                   当前登录用户
     * @param page                   分页信息
     * @return 用户消息分页
     */
    @PostMapping(FacilityMessageApiConstants.FETCH)
    @JsonResult
    public Page<MessageSearchDTO> fetch(@JsonParam MessageSearchCondition messageSearchCondition,
                                        @CurrentUser IUser user,
                                        @JsonPage(searchCount = true) Page<MessageSearchDTO> page) {
        if (messageSearchCondition == null) {
            messageSearchCondition = new MessageSearchCondition();
        }
        messageSearchCondition.setUser(user.getCode());
        return messageService.fetch(messageSearchCondition, page);
    }

}
