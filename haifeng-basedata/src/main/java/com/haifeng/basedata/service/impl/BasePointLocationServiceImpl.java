package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BasePointLocation;
import com.haifeng.basedata.mapper.BasePointLocationMapper;
import com.haifeng.basedata.service.IBasePointLocationService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BasePointLocationServiceImpl extends ServiceImpl<BasePointLocationMapper, BasePointLocation>
        implements IBasePointLocationService {
    @Autowired
    private BasePointLocationMapper mapper;

    @Override
    public void insertPointLocation(BasePointLocation basePointLocation) {
        checkRequiredFields(basePointLocation);
        checkPointLocationUnique(basePointLocation);
        mapper.insert(basePointLocation);
    }

    private void checkRequiredFields(BasePointLocation basePointLocation) {
        if (Objects.isNull(basePointLocation.getPointId())) {
            throw new ServiceException(MessageUtils.message("point.id.not.blank"));
        }
        if (Objects.isNull(basePointLocation.getLocationId())) {
            throw new ServiceException(MessageUtils.message("location.id.not.blank"));
        }
        if (StringUtils.isBlank(basePointLocation.getPointLocationType())) {
            throw new ServiceException(MessageUtils.message("relationship.type.not.blank"));
        }
    }

    private void checkPointLocationUnique(BasePointLocation basePointLocation) {
        QueryWrapper<BasePointLocation> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(BasePointLocation::getPointId, basePointLocation.getPointId())
                .eq(BasePointLocation::getLocationId, basePointLocation.getLocationId())
                .eq(BasePointLocation::getPointLocationType, basePointLocation.getPointLocationType());
        if (mapper.selectCount(wrapper) > 0) {
            throw new ServiceException(MessageUtils.message(
                    "point.location.exists", basePointLocation.getPointId(), basePointLocation.getLocationId()));
        }
    }

    @Override
    public void deletePointLocation(Long[] ids) {
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public void deleteByLocationId(Long locationId) {
        QueryWrapper<BasePointLocation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BasePointLocation::getLocationId, locationId);
        mapper.delete(wrapper);
    }

    @Override
    public BasePointLocation selectPointLocationById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<BasePointLocation> selectPointLocationList(BasePointLocation basePointLocation) {
        QueryWrapper<BasePointLocation> wrapper = new QueryWrapper<>();
        if (basePointLocation.getPointId() != null) {
            wrapper.lambda().eq(BasePointLocation::getPointId, basePointLocation.getPointId());
        }
        if (basePointLocation.getLocationId() != null) {
            wrapper.lambda().eq(BasePointLocation::getLocationId, basePointLocation.getLocationId());
        }
        if (StringUtils.isNotBlank(basePointLocation.getPointLocationType())) {
            wrapper.lambda().eq(BasePointLocation::getPointLocationType, basePointLocation.getPointLocationType());
        }
        return mapper.selectList(wrapper);
    }

    @Override
    public Long countByPointId(Long pointId) {
        QueryWrapper<BasePointLocation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BasePointLocation::getPointId, pointId);
        return mapper.selectCount(wrapper);
    }
}
