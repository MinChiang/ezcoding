package com.ezcoding.facility.module.message.bean.model;

import lombok.Data;

import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-19 10:20
 */
@Data
public class PersonalMessageSearchCondition {

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 收信人
     */
    private String receiver;

    /**
     * 寄信人
     */
    private String sender;

}
