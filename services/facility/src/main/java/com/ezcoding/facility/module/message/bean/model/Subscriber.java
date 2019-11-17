package com.ezcoding.facility.module.message.bean.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.sdk.facility.message.bean.dto.MessageSearchDTO;
import lombok.Data;

import java.util.Collection;

/**
 * 订阅人
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-14 15:22
 */
@Data
public class Subscriber {

    /**
     * 订阅人主键
     */
    private String code;

    /**
     * 订阅内容
     */
    private Collection<Subject> subjects;

    /**
     * 订阅
     *
     * @param subject 订阅目标
     */
    public void subscribe(Subject subject) {

    }

    /**
     * 取消订阅
     *
     * @param subject 订阅目标
     */
    public void unsubscribe(Subject subject) {

    }

    /**
     * 检索未读消息的数量
     *
     * @param condition 检索条件
     * @return 未读消息的数量
     */
    public Integer countUnread(MessageSearchCondition condition) {
        return 0;
    }

    /**
     * 读取消息
     *
     * @param user     读取消息的用户
     * @param messages 读取的消息id列表
     */
    public void read(String user, Collection<Long> messages) {

    }

    /**
     * 根据条件检索消息
     *
     * @param condition 检索条件
     * @param page      分页
     * @return 消息
     */
    public Page<MessageSearchDTO> fetch(MessageSearchCondition condition, Page<MessageSearchDTO> page) {
        return null;
    }

}
