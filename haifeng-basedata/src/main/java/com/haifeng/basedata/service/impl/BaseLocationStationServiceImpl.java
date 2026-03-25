package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.haifeng.basedata.domain.BaseLocationStation;
import com.haifeng.basedata.mapper.BaseLocationStationMapper;
import com.haifeng.basedata.service.IBaseLocationStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseLocationStationServiceImpl implements IBaseLocationStationService {

    @Autowired
    private BaseLocationStationMapper baseLocationStationMapper;

    @Override
    public void insertLocationStation(BaseLocationStation baseLocationStation) {
        baseLocationStationMapper.insert(baseLocationStation);
    }

    @Override
    public void deleteLocationStation(BaseLocationStation baseLocationStation) {
        LambdaQueryWrapper<BaseLocationStation> queryWrapper = new LambdaQueryWrapper<>();
        if (baseLocationStation.getLocationId() != null) {
            queryWrapper.eq(BaseLocationStation::getLocationId, baseLocationStation.getLocationId());
        }
        if (baseLocationStation.getStationId() != null) {
            queryWrapper.eq(BaseLocationStation::getStationId, baseLocationStation.getStationId());
        }
        baseLocationStationMapper.delete(queryWrapper);
    }

    @Override
    public List<BaseLocationStation> selectLocationStationList(BaseLocationStation baseLocationStation) {
        LambdaQueryWrapper<BaseLocationStation> queryWrapper = new LambdaQueryWrapper<>();
        if (baseLocationStation.getLocationId() != null) {
            queryWrapper.eq(BaseLocationStation::getLocationId, baseLocationStation.getLocationId());
        }
        if (baseLocationStation.getStationId() != null) {
            queryWrapper.eq(BaseLocationStation::getStationId, baseLocationStation.getStationId());
        }
        return baseLocationStationMapper.selectList(queryWrapper);
    }
}
