package com.ezcoding.facility.module.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.facility.module.message.bean.model.PersonalMessage;
import com.ezcoding.facility.module.message.bean.model.PersonalMessageSearchCondition;

import java.util.Collection;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-23 15:02
 */
public interface IPersonalMessageService {

    /**
     * 根据条件检索未读私信数量
     *
     * @param personalMessageSearchCondition 检索条件
     * @return 未读私信数量
     */
    Integer countUnread(PersonalMessageSearchCondition personalMessageSearchCondition);

    /**
     * 给用户发送私信
     *
     * @param content   私信内容
     * @param sender    发送者
     * @param receivers 收信人
     * @return 私信实例
     */
    List<PersonalMessage> send(String content, String sender, List<String> receivers);

    /**
     * 获取私信
     *
     * @param personalMessageSearchCondition 检索条件
     * @param page                           分页
     * @return 批量获取私信
     */
    Page<PersonalMessage> fetch(PersonalMessageSearchCondition personalMessageSearchCondition, Page<PersonalMessage> page);

    /**
     * 读取私信
     *
     * @param receiver 收信人
     * @param ids      读取的私信id
     */
    void read(String receiver, Collection<Long> ids);

    /**
     * 读取所有私信
     *
     * @param receiver 收信人
     */
    void readAll(String receiver);

    /**
     * 删除私信
     *
     * @param receiver 收信人
     * @param ids      删除的私信id
     */
    void delete(String receiver, Collection<Long> ids);

    /**
     * 读取所有私信
     *
     * @param receiver 收信人
     */
    void deleteAll(String receiver);

}
