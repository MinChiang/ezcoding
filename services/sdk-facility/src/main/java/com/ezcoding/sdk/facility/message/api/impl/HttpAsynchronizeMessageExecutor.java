package com.ezcoding.sdk.facility.message.api.impl;

import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.core.message.builder.RequestMessageBuilder;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.sdk.facility.message.api.IMessageSender;
import com.ezcoding.sdk.facility.message.api.MessageFeignClient;
import com.ezcoding.sdk.facility.message.bean.dto.MessageDTO;
import com.ezcoding.sdk.facility.message.bean.dto.SubscriptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-09 16:10
 */
@Slf4j
public class HttpAsynchronizeMessageExecutor extends ThreadPoolExecutor implements IMessageSender {

    private static final int DEFAULT_CORE_POOL_SIZE = 16;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 32;
    private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new ArrayBlockingQueue<>(100);
    private static final RejectedExecutionHandler DEFAULT_REJECTED_EXECUTION_HANDLER = (r, executor) -> {
        if (log.isErrorEnabled()) {
            log.error("已经超过系统处理能力，丢弃当前的执行项目");
        }
    };

    private MessageFeignClient messageFeignClient;

    public HttpAsynchronizeMessageExecutor() {
        this(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAXIMUM_POOL_SIZE, 0L, TimeUnit.SECONDS, DEFAULT_WORK_QUEUE, DEFAULT_REJECTED_EXECUTION_HANDLER);
    }

    public HttpAsynchronizeMessageExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public HttpAsynchronizeMessageExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public HttpAsynchronizeMessageExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public HttpAsynchronizeMessageExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    @Override
    public void subscribe(SubscriptionDTO subscription) throws IllegalArgumentException {
        AssertUtils.mustFalse(StringUtils.isEmpty(subscription.getApplicationCode()) ||
                        StringUtils.isEmpty(subscription.getScene()) ||
                        subscription.getSubjectId() == null ||
                        StringUtils.isEmpty(subscription.getSubscriber()),
                CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("系统号、场景号、订阅物品主键和订阅人不能为空").build());
        this.submit(() -> {
            messageFeignClient.subscribe(RequestMessageBuilder.create(subscription).build());
        });
    }

    @Override
    public void unsubscribe(SubscriptionDTO subscription) throws IllegalArgumentException {
        AssertUtils.mustNotEmpty(subscription.getSubscriber(), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("订阅人不能为空").build());
        AssertUtils.mustFalse(subscription.getId() == null && (StringUtils.isEmpty(subscription.getApplicationCode()) ||
                        StringUtils.isEmpty(subscription.getScene()) ||
                        subscription.getSubjectId() == null ||
                        StringUtils.isEmpty(subscription.getSubscriber())),
                CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("订阅主键或系统号、场景号、订阅物品主键不能为空").build());
        this.submit(() -> {
            messageFeignClient.unsubscribe(RequestMessageBuilder.create(subscription).build());
        });
    }

    @Override
    public void sendMessage(MessageDTO message) throws IllegalArgumentException {
        AssertUtils.mustFalse(StringUtils.isEmpty(message.getApplicationCode()) ||
                        StringUtils.isEmpty(message.getScene()) ||
                        message.getSubjectId() == null,
                CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("系统号、场景号、订阅物品主键").build())
        ;
        this.submit(() -> {
            messageFeignClient.record(RequestMessageBuilder.create(message).build());
        });
    }

    public void setMessageFeignClient(MessageFeignClient messageFeignClient) {
        this.messageFeignClient = messageFeignClient;
    }

    public MessageFeignClient getMessageFeignClient() {
        return messageFeignClient;
    }

}