package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseStation;
import com.haifeng.basedata.domain.BaseVisualArea;
import com.haifeng.basedata.mapper.BaseVisualAreaMapper;
import com.haifeng.basedata.service.IBaseVisualAreaService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BaseVisualAreaServiceImpl extends ServiceImpl<BaseVisualAreaMapper, BaseVisualArea>
        implements IBaseVisualAreaService {

    @Autowired
    private BaseVisualAreaMapper mapper;

    @Override
    public void insertVisualArea(BaseVisualArea baseVisualArea) {
        if (!checkVisualAreaCodeUnique(baseVisualArea)) {
            throw new ServiceException(MessageUtils.message("visualArea.code.exists"));
        }
        mapper.insert(baseVisualArea);
    }

    @Override
    public void updateVisualArea(BaseVisualArea baseVisualArea) {
        if (Objects.isNull(baseVisualArea.getId())) {
            throw new ServiceException(MessageUtils.message("visualArea.id.not.null"));
        }
        BaseVisualArea old = mapper.selectById(baseVisualArea.getId());
        if (old != null && !old.getVisualAreaCode().equals(baseVisualArea.getVisualAreaCode())) {
            if (!checkVisualAreaCodeUnique(baseVisualArea)) {
                throw new ServiceException(MessageUtils.message("visualArea.code.exists"));
            }
        }
        mapper.updateById(baseVisualArea);
    }

    @Override
    public void deleteVisualArea(Long[] ids) {
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public BaseVisualArea selectVisualAreaById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<BaseVisualArea> selectVisualAreaList(BaseVisualArea baseVisualArea) {
        QueryWrapper<BaseVisualArea> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(baseVisualArea.getVisualAreaCode())) {
            wrapper.lambda().eq(BaseVisualArea::getVisualAreaCode, baseVisualArea.getVisualAreaCode());
        }
        if (StringUtils.hasText(baseVisualArea.getVisualAreaName())) {
            wrapper.lambda().like(BaseVisualArea::getVisualAreaName, baseVisualArea.getVisualAreaName());
        }
        wrapper.lambda().orderByDesc(BaseVisualArea::getCreateTime);
        return mapper.selectList(wrapper);
    }

    public Boolean checkVisualAreaCodeUnique(BaseVisualArea baseVisualArea) {
        QueryWrapper<BaseVisualArea> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BaseVisualArea::getVisualAreaCode, baseVisualArea.getVisualAreaCode());
        return mapper.selectCount(wrapper) == 0;
    }
}
