package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseContainer;
import com.haifeng.basedata.domain.BaseContainerModel;
import com.haifeng.basedata.mapper.BaseContainerModelMapper;
import com.haifeng.basedata.service.IBaseContainerModelService;
import com.haifeng.basedata.service.IBaseContainerService;
import com.haifeng.common.constant.UserConstants;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.DictUtils;
import com.haifeng.common.utils.MessageUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【base_container_model(容器模型表)】的数据库操作Service实现
* @createDate 2026-02-28 09:45:53
*/
@Service
public class BaseContainerModelServiceImpl extends ServiceImpl<BaseContainerModelMapper, BaseContainerModel>
    implements IBaseContainerModelService {

    @Autowired
    private BaseContainerModelMapper baseContainerModelMapper;

    @Autowired
    @Lazy
    private IBaseContainerService baseContainerService;

    @Override
    public List<BaseContainerModel> selectBaseContainerModelList(BaseContainerModel baseContainerModel) {
        LambdaQueryWrapper<BaseContainerModel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(baseContainerModel.getContainerModelName()), BaseContainerModel::getContainerModelName, baseContainerModel.getContainerModelName())
                .like(StringUtils.isNotBlank(baseContainerModel.getContainerModelCode()), BaseContainerModel::getContainerModelCode, baseContainerModel.getContainerModelCode())
                .eq(StringUtils.isNotBlank(baseContainerModel.getContainerBasicType()), BaseContainerModel::getContainerBasicType, baseContainerModel.getContainerBasicType());
        List<BaseContainerModel> baseContainerModelList = baseContainerModelMapper.selectList(queryWrapper);
        for (BaseContainerModel containerModel : baseContainerModelList) {
            containerModel.setContainerBasicTypeDesc(DictUtils.getDictLabel("container_basic_type", containerModel.getContainerBasicType()));
        }
        return baseContainerModelList;
    }

    @Override
    public int updateBaseContainerModel(BaseContainerModel baseContainerModel) {
        if (baseContainerModel.getId() ==  null) {
            throw new ServiceException(MessageUtils.message("update.container.model.id.required"));
        }
        // 修改时禁止修改容器模型编号，如果传了编号则校验
        if (StringUtils.isNotBlank(baseContainerModel.getContainerModelCode())) {
            BaseContainerModel oldModel = baseContainerModelMapper.selectById(baseContainerModel.getId());
            if (oldModel != null && !oldModel.getContainerModelCode().equals(baseContainerModel.getContainerModelCode())) {
                throw new ServiceException(MessageUtils.message("update.container.model.code.forbidden"));
            }
        }
        return baseContainerModelMapper.updateById(baseContainerModel);
    }

    @Override
    public void addBaseContainerModel(BaseContainerModel baseContainerModel) {
        if (!checkContainerCodeUnique(baseContainerModel)) {
            throw new ServiceException(MessageUtils.message("container.model.code.already.exists"));
        }
        baseContainerModelMapper.insert(baseContainerModel);
    }

    @Override
    public BaseContainerModel getByCode(String containerModelCode) {
        LambdaQueryWrapper<BaseContainerModel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseContainerModel::getContainerModelCode, containerModelCode);
        return baseContainerModelMapper.selectOne(queryWrapper);
    }

    @Override
    public int removeContainerModel(List<Long> ids) {
        // 校验是否有关联的容器，存在关联的容器，禁止删除
        for (Long id : ids) {
            BaseContainerModel containerModel = baseContainerModelMapper.selectById(id);
            if (containerModel == null) {
                continue;
            }
            
            // 查询是否有容器关联了该容器模型
            List<BaseContainer> containers = baseContainerService.selectBaseContainerList(
                BaseContainer.builder().containerModelCode(containerModel.getContainerModelCode()).build()
            );
            
            if (CollectionUtils.isNotEmpty(containers)) {
                throw new ServiceException(
                    MessageUtils.message("container.model.has.associated.containers", 
                        containerModel.getContainerModelCode(), 
                        containerModel.getContainerModelName())
                );
            }
        }
        
        // 执行删除
        return baseContainerModelMapper.deleteBatchIds(ids);
    }

    private boolean checkContainerCodeUnique(BaseContainerModel baseContainerModel) {
        LambdaQueryWrapper<BaseContainerModel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseContainerModel::getContainerModelCode, baseContainerModel.getContainerModelCode());
        List<BaseContainerModel> baseContainerModelList = baseContainerModelMapper.selectList(queryWrapper);
        baseContainerModelList = baseContainerModelList.stream().filter(b -> !b.getId().equals(baseContainerModel.getId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(baseContainerModelList)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}




