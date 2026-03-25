package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseLocationLock;
import com.haifeng.basedata.mapper.BaseLocationLockMapper;
import com.haifeng.basedata.service.IBaseLocationLockService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseLocationLockServiceImpl extends ServiceImpl<BaseLocationLockMapper, BaseLocationLock>
        implements IBaseLocationLockService {
    @Autowired
    private BaseLocationLockMapper mapper;

    @Override
    public void insertLocationLock(BaseLocationLock baseLocationLock) {
        checkRequiredFields(baseLocationLock);
        checkLocationLockUnique(baseLocationLock);
        mapper.insert(baseLocationLock);
    }

    private void checkRequiredFields(BaseLocationLock baseLocationLock) {
        if (StringUtils.isBlank(baseLocationLock.getLocationCode())) {
            throw new ServiceException(MessageUtils.message("location.code.not.blank"));
        }
        if (StringUtils.isBlank(baseLocationLock.getLockSource())) {
            throw new ServiceException(MessageUtils.message("lock.source.not.blank"));
        }
    }

    private void checkLocationLockUnique(BaseLocationLock baseLocationLock) {
        QueryWrapper<BaseLocationLock> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BaseLocationLock::getLocationCode, baseLocationLock.getLocationCode());
        if (mapper.selectCount(wrapper) > 0) {
            throw new ServiceException(MessageUtils.message("location.lock.exists", baseLocationLock.getLocationCode()));
        }
    }

    @Override
    public void deleteLocationLock(BaseLocationLock baseLocationLock) {
        QueryWrapper<BaseLocationLock> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(StringUtils.isNotBlank(baseLocationLock.getLocationCode()),
                        BaseLocationLock::getLocationCode, baseLocationLock.getLocationCode())
                .eq(StringUtils.isNotBlank(baseLocationLock.getLockSource()),
                        BaseLocationLock::getLockSource, baseLocationLock.getLockSource());
        mapper.delete(wrapper);
    }
}
