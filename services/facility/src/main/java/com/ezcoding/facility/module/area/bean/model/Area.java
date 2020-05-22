package com.ezcoding.facility.module.area.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.sdk.facility.area.bean.model.LevelEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-19 14:26
 */
@Data
@TableName("sys_area")
public class Area implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 层级
     */
    private LevelEnum level;

    /**
     * 行政代码
     */
    private Long areaCode;

    /**
     * 邮政编码
     */
    private Integer zipCode;

    /**
     * 区号
     */
    private String cityCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 组合名
     */
    private String mergerName;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 经度
     */
    private BigDecimal lng;

    /**
     * 纬度
     */
    private BigDecimal lat;

}
