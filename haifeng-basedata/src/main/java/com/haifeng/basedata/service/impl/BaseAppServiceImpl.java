package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseApp;
import com.haifeng.basedata.mapper.BaseAppMapper;
import com.haifeng.basedata.service.IBaseAppService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BaseAppServiceImpl extends ServiceImpl<BaseAppMapper, BaseApp> implements IBaseAppService {

    @Autowired
    private BaseAppMapper mapper;

    @Override
    public void insertApp(BaseApp baseApp) {
        if (!checkAppCodeUnique(baseApp)) {
            throw new ServiceException(MessageUtils.message("app.code.exists"));
        }
        mapper.insert(baseApp);
    }

    @Override
    public void updateApp(BaseApp baseApp) {
        if (Objects.isNull(baseApp.getId())) {
            throw new ServiceException(MessageUtils.message("app.id.not.null"));
        }
        BaseApp old = mapper.selectById(baseApp.getId());
        if (old != null && !old.getAppCode().equals(baseApp.getAppCode())) {
            if (!checkAppCodeUnique(baseApp)) {
                throw new ServiceException(MessageUtils.message("app.code.exists"));
            }
        }
        mapper.updateById(baseApp);
    }

    @Override
    public void deleteApp(Long[] ids) {
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public BaseApp selectAppById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<BaseApp> selectAppList(BaseApp baseApp) {
        QueryWrapper<BaseApp> wrapper = new QueryWrapper<>();
        if (baseApp.getAppCode() != null) {
            wrapper.lambda().eq(BaseApp::getAppCode, baseApp.getAppCode());
        }
        if (baseApp.getAppName() != null) {
            wrapper.lambda().like(BaseApp::getAppName, baseApp.getAppName());
        }
        wrapper.lambda().orderByDesc(BaseApp::getCreateTime);
        return mapper.selectList(wrapper);
    }

    public Boolean checkAppCodeUnique(BaseApp baseApp) {
        QueryWrapper<BaseApp> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BaseApp::getAppCode, baseApp.getAppCode());
        return mapper.selectCount(wrapper) == 0;
    }
}
