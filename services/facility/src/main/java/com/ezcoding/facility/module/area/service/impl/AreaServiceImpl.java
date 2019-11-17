package com.ezcoding.facility.module.area.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.facility.module.area.bean.model.Area;
import com.ezcoding.facility.module.area.dao.AreaMapper;
import com.ezcoding.facility.module.area.service.IAreaService;
import com.ezcoding.sdk.facility.area.bean.dto.AreaSearchDTO;
import com.ezcoding.sdk.facility.area.bean.model.LevelEnum;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-19 14:37
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

    @Override
    public Page<Area> searchArea(AreaSearchDTO areaSearchDTO, Page<Area> page) {
        List<Area> areas = baseMapper.selectAreaByCondition(areaSearchDTO, page);
        page.setRecords(areas);
        return page;
    }

    @Override
    public Collection<Area> searchAreaByIds(Set<Long> ids) {
        return listByIds(ids);
    }

    @Override
    public boolean checkAffiliation(Long fatherAreaId, Long sonAreaId, Integer level) {
        return false;
    }

    @Override
    public boolean checkDirectAffiliation(Long fatherAreaId, Long sonAreaId) {
        Area area = getById(sonAreaId);
        if (area == null) {
            return false;
        }
        return Objects.equals(fatherAreaId, area.getParentId());
    }

    @Override
    public boolean checkLevel(Long areaId, LevelEnum level) {
        Area area = new Area();
        area.setId(areaId);
        area.setLevel(level);

        return getOne(Wrappers.query(area)) != null;
    }

}
