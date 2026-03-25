package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.haifeng.basedata.domain.BasePointStation;
import com.haifeng.basedata.mapper.BasePointStationMapper;
import com.haifeng.basedata.service.IBasePointStationService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasePointStationServiceImpl implements IBasePointStationService {

    @Autowired
    private BasePointStationMapper basePointStationMapper;

    @Override
    public void insertPointStation(BasePointStation basePointStation) {
        LambdaQueryWrapper<BasePointStation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BasePointStation::getStationId, basePointStation.getStationId())
                .eq(BasePointStation::getPointId, basePointStation.getPointId());
        if (basePointStationMapper.selectCount(wrapper) > 0) {
            throw new ServiceException(MessageUtils.message("station.point.exits"));
        }
        basePointStationMapper.insert(basePointStation);
    }

    @Override
    public void deletePointStation(BasePointStation basePointStation) {
        LambdaQueryWrapper<BasePointStation> queryWrapper = new LambdaQueryWrapper<>();
        if (basePointStation.getStationId() != null) {
            queryWrapper.eq(BasePointStation::getStationId, basePointStation.getStationId());
        }
        if (basePointStation.getPointId() != null) {
            queryWrapper.eq(BasePointStation::getPointId, basePointStation.getPointId());
        }
        basePointStationMapper.delete(queryWrapper);
    }

    @Override
    public BasePointStation selectByPointId(Long pointId) {

        return basePointStationMapper.selectStationCodeByPointId(pointId);
    }
}
