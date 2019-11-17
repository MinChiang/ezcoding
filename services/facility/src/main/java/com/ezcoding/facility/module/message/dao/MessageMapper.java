package com.ezcoding.facility.module.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.facility.module.message.bean.model.Message;
import com.ezcoding.facility.module.message.bean.model.MessageSearchCondition;
import com.ezcoding.sdk.facility.message.bean.dto.MessageSearchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-19 10:59
 */
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 根据条件统计用户未读消息的数量
     *
     * @param condition 筛选的条件
     * @return 用户未读消息的数量
     */
    Integer selectUnreadCountByCondition(@Param("condition") MessageSearchCondition condition);

    /**
     * 根据条件筛选用户的消息
     *
     * @param condition 筛选的条件
     * @param page      分页信息
     * @return 用户消息分页
     */
    List<MessageSearchDTO> selectListByCondition(@Param("condition") MessageSearchCondition condition, Page<MessageSearchDTO> page);

    /**
     * 筛选出已经读取的消息
     *
     * @param user 当前用户
     * @param ids  待筛选的数据
     * @return 已经读取过的消息
     */
    List<Long> selectExistAndUnread(@Param("user") String user, @Param("ids") Collection<Long> ids);

}
