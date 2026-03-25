package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseStation;
import com.haifeng.basedata.mapper.BaseStationMapper;
import com.haifeng.basedata.service.IBaseStationService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BaseStationServiceImpl extends ServiceImpl<BaseStationMapper, BaseStation> implements IBaseStationService {
    @Autowired
    private BaseStationMapper mapper;

    @Override
    public void insertStation(BaseStation baseStation) {
        if (!checkStationCodeUnique(baseStation)){
            throw new ServiceException(MessageUtils.message("station.code.exists"));
        }
        mapper.insert(baseStation);
    }

    @Override
    public void updateStation(BaseStation baseStation) {
        BaseStation old = mapper.selectById(baseStation.getId());
        if (old != null && !old.getStationCode().equals(baseStation.getStationCode())){
            if (!checkStationCodeUnique(baseStation)){
                throw new ServiceException(MessageUtils.message("station.code.exists"));
            }
        }
        mapper.updateById(baseStation);
    }

    @Override
    public void deleteStation(Long[] ids) {
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public BaseStation selectStationById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<BaseStation> selectStationList(BaseStation baseStation) {
        QueryWrapper<BaseStation> wrapper = new QueryWrapper<>();
        if (baseStation.getStationCode() != null) {
            wrapper.lambda().like(BaseStation::getStationCode, baseStation.getStationCode());
        }
        if (baseStation.getStationName() != null) {
            wrapper.lambda().like(BaseStation::getStationName, baseStation.getStationName());
        }
        wrapper.lambda().orderByDesc(BaseStation::getCreateTime);
        return mapper.selectList(wrapper);
    }

    public Boolean checkStationCodeUnique(BaseStation baseStation) {
        QueryWrapper<BaseStation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BaseStation::getStationCode, baseStation.getStationCode());
        return mapper.selectCount(wrapper) == 0;
    }
}
