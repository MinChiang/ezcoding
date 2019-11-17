package com.ezcoding.facility.module.message.bean.model;

import lombok.Data;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 订阅目标
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-14 15:23
 */
@Data
public class Subject {

    /**
     * 主体系统号
     */
    private String applicationCode;

    /**
     * 主体场景号
     */
    private String scene;

    /**
     * 订阅主体id
     */
    private Long key;

    /**
     * 发生的消息
     */
    private Collection<Message> messages = new LinkedList<>();

    /**
     * 记录消息
     *
     * @param message 消息内容
     */
    public void record(Message message) {
        this.messages.add(message);
    }

}
