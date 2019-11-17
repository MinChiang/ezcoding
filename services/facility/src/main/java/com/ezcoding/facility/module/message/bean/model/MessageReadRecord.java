package com.ezcoding.facility.module.message.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-20 14:20
 */
@TableName("facility_message_read_record")
@Data
public class MessageReadRecord implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 消息主键
     */
    private Long messageId;

    /**
     * 读取消息的用户
     */
    private String user;

    /**
     * 消息读取时间
     */
    private Date readTime;

}
