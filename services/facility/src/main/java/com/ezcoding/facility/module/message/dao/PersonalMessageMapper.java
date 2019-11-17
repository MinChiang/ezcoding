package com.ezcoding.facility.module.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.facility.module.message.bean.model.PersonalMessage;
import com.ezcoding.facility.module.message.bean.model.PersonalMessageSearchCondition;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-23 15:21
 */
public interface PersonalMessageMapper extends BaseMapper<PersonalMessage> {

    /**
     * 根据条件统计用户未读消息的数量
     *
     * @param condition 筛选的条件
     * @return 用户未读私信的数量
     */
    Integer selectUnreadCountByCondition(@Param("condition") PersonalMessageSearchCondition condition);

    /**
     * 根据条件筛选用户的消息
     *
     * @param condition 筛选的条件
     * @param page      分页信息
     * @return 用户消息分页
     */
    List<PersonalMessage> selectListByCondition(@Param("condition") PersonalMessageSearchCondition condition, Page<PersonalMessage> page);

    /**
     * 更新私信为已读状态
     *
     * @param receiver 收信人
     * @param ids      待更新的私信
     */
    void updateReadStatus(@Param("receiver") String receiver, @Param("ids") Collection<Long> ids);

    /**
     * 更新私信为已读状态
     *
     * @param receiver 收信人
     * @param ids      待更新的私信
     */
    void deleteBatch(@Param("receiver") String receiver, @Param("ids") Collection<Long> ids);

}
