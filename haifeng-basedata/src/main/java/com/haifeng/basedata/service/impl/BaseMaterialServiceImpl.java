package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseMaterial;
import com.haifeng.basedata.mapper.BaseMaterialMapper;
import com.haifeng.basedata.service.IBaseMaterialCategoryService;
import com.haifeng.basedata.service.IBaseMaterialService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BaseMaterialServiceImpl extends ServiceImpl<BaseMaterialMapper, BaseMaterial>
        implements IBaseMaterialService {

    @Autowired
    private BaseMaterialMapper mapper;

    @Autowired
    private IBaseMaterialCategoryService categoryService;

    @Override
    public void insertMaterial(BaseMaterial material) {
        if (!categoryService.checkCategoryIdExists(material.getCategoryId())) {
            throw new ServiceException(MessageUtils.message("material.category.id.not.exists"));
        }
        if (!checkMaterialCodeUnique(material)) {
            throw new ServiceException(MessageUtils.message("material.code.already.exists"));
        }
        mapper.insert(material);
    }

    @Override
    public void updateMaterial(BaseMaterial material) {
        if (Objects.isNull(material.getId())) {
            throw new ServiceException(MessageUtils.message("material.id.not.null"));
        }
        if (!categoryService.checkCategoryIdExists(material.getCategoryId())) {
            throw new ServiceException(MessageUtils.message("material.category.id.not.exists"));
        }
        QueryWrapper<BaseMaterial> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BaseMaterial::getId, material.getId());
        BaseMaterial old = mapper.selectOne(wrapper);
        if (old != null && !old.getMaterialCode().equals(material.getMaterialCode())) {
            if (!checkMaterialCodeUnique(material)) {
                throw new ServiceException(MessageUtils.message("material.code.already.exists"));
            }
        }
        mapper.updateById(material);
    }

    @Override
    public void deleteMaterial(Long[] ids) {
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public BaseMaterial selectMaterialById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<BaseMaterial> selectMaterialList(BaseMaterial material) {
        return mapper.selectMaterialListWithCategory(material);
    }

    public Boolean checkMaterialCodeUnique(BaseMaterial material) {
        LambdaQueryWrapper<BaseMaterial> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseMaterial::getMaterialCode, material.getMaterialCode());
        return mapper.selectCount(wrapper) == 0;
    }

    @Override
    public List<BaseMaterial> selectMaterialListByParentCategoryId(Long parentId) {
        List<Long> categoryIds = categoryService.selectAllCategoryIdsByParentId(parentId);
        if (CollectionUtils.isEmpty(categoryIds)) {
            return new ArrayList<>();
        }
        QueryWrapper<BaseMaterial> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(BaseMaterial::getCategoryId, categoryIds);
        return mapper.selectList(wrapper);
    }
}
