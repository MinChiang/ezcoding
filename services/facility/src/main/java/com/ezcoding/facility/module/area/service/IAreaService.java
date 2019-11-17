package com.ezcoding.facility.module.area.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ezcoding.facility.module.area.bean.model.Area;
import com.ezcoding.sdk.facility.area.bean.dto.AreaSearchDTO;
import com.ezcoding.sdk.facility.area.bean.model.LevelEnum;

import java.util.Collection;
import java.util.Set;


/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-19 14:40
 */
public interface IAreaService extends IService<Area> {

    /**
     * 搜索地区信息
     *
     * @param areaSearchDTO 检索条件
     * @param page          分页信息
     * @return 地区信息
     */
    Page<Area> searchArea(AreaSearchDTO areaSearchDTO, Page<Area> page);

    /**
     * 根据主键批量搜索地区信息
     *
     * @param ids 主键
     * @return 地区信息
     */
    Collection<Area> searchAreaByIds(Set<Long> ids);

    /**
     * 检查父子区域的从属关系
     * 例如：
     * 广东省 - 天河区 : true
     * 广州市 - 荔湾区 : true
     * 湖南省 - 海珠区 : false
     *
     * @param fatherAreaId 父区域id
     * @param sonAreaId    子区域id
     * @param level        相隔层数
     * @return 是否满足从属关系
     */
    boolean checkAffiliation(Long fatherAreaId, Long sonAreaId, Integer level);

    /**
     * 检查父子区域的直接从属关系
     * 例如：
     * 广东省 - 天河区 : false
     * 广州市 - 荔湾区 : true
     * 湖南省 - 海珠区 : false
     *
     * @param fatherAreaId 父区域id
     * @param sonAreaId    子区域id
     * @return 是否满足直接从属关系
     */
    boolean checkDirectAffiliation(Long fatherAreaId, Long sonAreaId);

    /**
     * 查询id是否满足层级
     *
     * @param areaId 区域id
     * @param level  层级
     * @return 层级
     */
    boolean checkLevel(Long areaId, LevelEnum level);

}
