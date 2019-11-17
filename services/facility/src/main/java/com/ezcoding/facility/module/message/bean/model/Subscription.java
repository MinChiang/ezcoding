package com.ezcoding.facility.module.message.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-10 10:35
 */
@TableName("facility_subscription")
@Data
public class Subscription implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 订阅人
     */
    private String subscriber;

    /**
     * 订阅场景
     */
    private String scene;

    /**
     * 订阅物品id
     */
    private Long subjectId;

    /**
     * 系统号
     */
    private String applicationCode;

    /**
     * 订阅时间
     */
    private Date subscribeTime;

}
