package com.ezcoding.facility.module.message.bean.model;

import lombok.Data;

import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-19 10:20
 */
@Data
public class MessageSearchCondition {

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 检索用户
     */
    private String user;

    /**
     * 消息类型
     */
    private String applicationCode;

    /**
     * 场景号
     */
    private String scene;

}
