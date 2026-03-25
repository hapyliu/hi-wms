package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseLocationContainer;
import com.haifeng.basedata.mapper.BaseLocationContainerMapper;
import com.haifeng.basedata.service.IBaseLocationContainerService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseLocationContainerServiceImpl extends ServiceImpl<BaseLocationContainerMapper, BaseLocationContainer>
        implements IBaseLocationContainerService {
    @Autowired
    private BaseLocationContainerMapper mapper;

    @Override
    public void insertLocationContainer(BaseLocationContainer baseLocationContainer) {
        checkRequiredFields(baseLocationContainer);
        checkLocationContainerUnique(baseLocationContainer);
        mapper.insert(baseLocationContainer);
    }

    private void checkRequiredFields(BaseLocationContainer baseLocationContainer) {
        if (StringUtils.isBlank(baseLocationContainer.getLocationCode())) {
            throw new ServiceException(MessageUtils.message("location.code.not.blank"));
        }
        if (StringUtils.isBlank(baseLocationContainer.getContainerCode())) {
            throw new ServiceException(MessageUtils.message("container.code.not.blank"));
        }
    }

    private void checkLocationContainerUnique(BaseLocationContainer baseLocationContainer) {
        QueryWrapper<BaseLocationContainer> locationWrapper = new QueryWrapper<>();
        locationWrapper.lambda().eq(BaseLocationContainer::getLocationCode, baseLocationContainer.getLocationCode());
        if (mapper.selectCount(locationWrapper) > 0) {
            throw new ServiceException(MessageUtils.message("location.container.location.exists", baseLocationContainer.getLocationCode()));
        }

        QueryWrapper<BaseLocationContainer> containerWrapper = new QueryWrapper<>();
        containerWrapper.lambda().eq(BaseLocationContainer::getContainerCode, baseLocationContainer.getContainerCode());
        if (mapper.selectCount(containerWrapper) > 0) {
            throw new ServiceException(MessageUtils.message("location.container.container.exists", baseLocationContainer.getContainerCode()));
        }
    }

    @Override
    public void deleteLocationContainer(BaseLocationContainer baseLocationContainer) {
        QueryWrapper<BaseLocationContainer> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(StringUtils.isNotBlank(baseLocationContainer.getLocationCode()),
                        BaseLocationContainer::getLocationCode, baseLocationContainer.getLocationCode())
                .eq(StringUtils.isNotBlank(baseLocationContainer.getContainerCode()),
                        BaseLocationContainer::getContainerCode, baseLocationContainer.getContainerCode());
        mapper.delete(wrapper);
    }

    @Override
    public List<BaseLocationContainer> selectLocationContainerList(BaseLocationContainer baseLocationContainer) {
        QueryWrapper<BaseLocationContainer> wrapper = new QueryWrapper<>();
        if (baseLocationContainer != null) {
            wrapper.lambda()
                    .eq(StringUtils.isNotBlank(baseLocationContainer.getLocationCode()),
                            BaseLocationContainer::getLocationCode, baseLocationContainer.getLocationCode())
                    .eq(StringUtils.isNotBlank(baseLocationContainer.getContainerCode()),
                            BaseLocationContainer::getContainerCode, baseLocationContainer.getContainerCode());
        }
        return mapper.selectList(wrapper);
    }
}
