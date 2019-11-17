package com.ezcoding.account.module.management.bean.model;

import com.ezcoding.base.web.extend.mybatis.model.BaseModel;
import com.ezcoding.sdk.account.management.bean.model.PositionStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 11:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Position extends BaseModel implements Serializable {

    /**
     * 所属部门
     */
    private Long departmentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 唯一编号
     */
    private String code;

    /**
     * 描述
     */
    private String comment;

    /**
     * 状态
     */
    private PositionStatusEnum status;

}
