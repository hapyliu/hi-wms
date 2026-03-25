package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseApp;
import com.haifeng.basedata.domain.BaseArea;
import com.haifeng.basedata.mapper.BaseAreaMapper;
import com.haifeng.basedata.service.IBaseAreaService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BaseAreaServiceImpl extends ServiceImpl<BaseAreaMapper, BaseArea> implements IBaseAreaService {

    @Autowired
    private BaseAreaMapper mapper;

    @Override
    public void insertArea(BaseArea baseArea) {
        if (!checkAreaCodeUnique(baseArea)){
            throw new ServiceException(MessageUtils.message("area.code.exists"));
        }
        mapper.insert(baseArea);
    }

    @Override
    public void updateArea(BaseArea baseArea) {
        if (Objects.isNull(baseArea.getId())){
            throw new ServiceException(MessageUtils.message("area.id.not.null"));
        }
        BaseArea old = mapper.selectById(baseArea.getId());
        if (old != null && !old.getAreaCode().equals(baseArea.getAreaCode())){
            if (!checkAreaCodeUnique(baseArea)){
                throw new ServiceException(MessageUtils.message("area.code.exists"));
            }
        }
        mapper.updateById(baseArea);
    }

    @Override
    public void deleteArea(Long[] ids) {
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public BaseArea selectAreaById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<BaseArea> selectAreaList(BaseArea baseArea) {
        QueryWrapper<BaseArea> wrapper = new QueryWrapper<>();
        if (baseArea.getAreaCode() != null) {
            wrapper.lambda().eq(BaseArea::getAreaCode, baseArea.getAreaCode());
        }
        if (baseArea.getAreaName() != null) {
            wrapper.lambda().like(BaseArea::getAreaName, baseArea.getAreaName());
        }
        wrapper.lambda().orderByDesc(BaseArea::getCreateTime);
        return mapper.selectList(wrapper);
    }

    public Boolean checkAreaCodeUnique(BaseArea baseArea) {
        QueryWrapper<BaseArea> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BaseArea::getAreaCode, baseArea.getAreaCode());
        return mapper.selectCount(wrapper) == 0;
    }
}
