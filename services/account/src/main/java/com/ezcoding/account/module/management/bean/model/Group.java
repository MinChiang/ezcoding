package com.ezcoding.account.module.management.bean.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.base.web.extend.mybatis.model.BaseModel;
import com.ezcoding.sdk.account.management.bean.model.GroupStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 10:25
 */
@Data
@TableName("account_group")
@EqualsAndHashCode(callSuper = true)
public class Group extends BaseModel implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String comment;

    /**
     * 状态
     */
    private GroupStatusEnum status;

}
