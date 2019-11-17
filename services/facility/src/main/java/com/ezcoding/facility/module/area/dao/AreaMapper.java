package com.ezcoding.facility.module.area.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.facility.module.area.bean.model.Area;
import com.ezcoding.sdk.facility.area.bean.dto.AreaSearchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-19 14:37
 */
public interface AreaMapper extends BaseMapper<Area> {

    /**
     * 搜索地区信息
     *
     * @param areaSearchDTO 检索条件
     * @param page          分页信息
     * @return 地区信息
     */
    List<Area> selectAreaByCondition(@Param("areaSearchDTO") AreaSearchDTO areaSearchDTO, Page<Area> page);

}
