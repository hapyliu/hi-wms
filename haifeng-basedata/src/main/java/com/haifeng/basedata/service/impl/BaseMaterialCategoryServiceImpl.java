package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseMaterial;
import com.haifeng.basedata.domain.BaseMaterialCategory;
import com.haifeng.basedata.mapper.BaseMaterialCategoryMapper;
import com.haifeng.basedata.service.IBaseMaterialCategoryService;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BaseMaterialCategoryServiceImpl extends ServiceImpl<BaseMaterialCategoryMapper, BaseMaterialCategory>
        implements IBaseMaterialCategoryService {

    @Autowired
    private BaseMaterialCategoryMapper mapper;

    @Override
    public void insertCategory(BaseMaterialCategory category) {
        if (!checkCategoryCodeUnique(category)) {
            throw new ServiceException(MessageUtils.message("material.category.code.already.exists"));
        }
        // 设置祖级列表
        setAncestors(category);
        mapper.insert(category);
    }

    @Override
    public void updateCategory(BaseMaterialCategory category) {
        if (Objects.isNull(category.getId())) {
            throw new ServiceException(MessageUtils.message("material.category.id.not.null"));
        }
        LambdaQueryWrapper<BaseMaterialCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseMaterialCategory::getId, category.getId());
        BaseMaterialCategory old = mapper.selectOne(wrapper);
        if (old != null && !old.getCategoryCode().equals(category.getCategoryCode())) {
            if (!checkCategoryCodeUnique(category)) {
                throw new ServiceException(MessageUtils.message("material.category.code.already.exists"));
            }
        }
        // 设置祖级列表
        setAncestors(category);
        // 设置子节点的祖级列表
        setChildrenAncestors(category);
        mapper.updateById(category);
    }

    private void setChildrenAncestors(BaseMaterialCategory category) {
        BaseMaterialCategory parentCategory = mapper.selectById(category.getParentId());
        BaseMaterialCategory oldCategory = mapper.selectById(category.getId());
        if (StringUtils.isNotNull(parentCategory) && StringUtils.isNotNull(oldCategory)) {
            String newAncestors = StringUtils.isNotBlank(parentCategory.getAncestors()) ? parentCategory.getAncestors() + "," + parentCategory.getId() : parentCategory.getId().toString();
            String oldAncestors = oldCategory.getAncestors();
            if (!StringUtils.equals(oldAncestors, newAncestors)) {
                List<BaseMaterialCategory> children = mapper.selectChildrenCategoryById(category.getId());
                if (CollectionUtils.isNotEmpty(children)) {
                    for (BaseMaterialCategory child : children) {
                        child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
                    }
                    mapper.updateCategoryChildren(children);
                }
            }
        }
    }

    @Override
    public void deleteCategory(Long id) {
        // 检查是否有子节点
        List<BaseMaterialCategory> children = mapper.selectChildrenCategoryById(id);
        if (CollectionUtils.isNotEmpty(children)) {
            throw new ServiceException(MessageUtils.message("material.category.has.children"));
        }
        // 检查该分类下是否有物料（包括子孙分类）
        List<BaseMaterial> materials = mapper.selectMaterialsByCategoryId(id);
        if (CollectionUtils.isNotEmpty(materials)) {
            throw new ServiceException(MessageUtils.message("material.category.has.materials"));
        }
        mapper.deleteById(id);
    }

    @Override
    public BaseMaterialCategory selectCategoryById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<BaseMaterialCategory> selectCategoryList(BaseMaterialCategory category) {
        LambdaQueryWrapper<BaseMaterialCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(category.getCategoryCode()), BaseMaterialCategory::getCategoryCode, category.getCategoryCode())
                .like(StringUtils.isNotBlank(category.getCategoryName()), BaseMaterialCategory::getCategoryName, category.getCategoryName());
        return mapper.selectList(wrapper);
    }

    public Boolean checkCategoryCodeUnique(BaseMaterialCategory category) {
        LambdaQueryWrapper<BaseMaterialCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseMaterialCategory::getCategoryCode, category.getCategoryCode());
        return mapper.selectCount(wrapper) == 0;
    }

    @Override
    public List<Long> selectAllCategoryIdsByParentId(Long parentId) {
        List<Long> result = new ArrayList<>();
        // 将自身加入
        result.add(parentId);
        // 递归查找所有的子集
        findChildrenCategoryIds(parentId, result);
        return result;
    }

    @Override
    public boolean checkCategoryIdExists(Long categoryId) {
        BaseMaterialCategory baseMaterialCategory = mapper.selectById(categoryId);
        return baseMaterialCategory != null;
    }

    private void findChildrenCategoryIds(Long parentId, List<Long> result) {
        LambdaQueryWrapper<BaseMaterialCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseMaterialCategory::getParentId, parentId);
        List<BaseMaterialCategory> children = mapper.selectList(wrapper);
        if (children != null && !children.isEmpty()) {
            for (BaseMaterialCategory child : children) {
                result.add(child.getId());
                findChildrenCategoryIds(child.getId(), result);
            }
        }
    }

    /**
     * 设置祖级列表
     * @param category 物料分类
     */
    private void setAncestors(BaseMaterialCategory category) {
        Long parentId = category.getParentId();
        if (parentId == null || parentId <= 0) {
            // 顶级分类，祖级列表为空或只包含自身 ID（根据需求决定）
            category.setAncestors("");
        } else {
            // 查询父分类的祖级列表
            BaseMaterialCategory parent = mapper.selectById(parentId);
            if (parent != null) {
                String parentAncestors = parent.getAncestors();
                if (StringUtils.isNotBlank(parentAncestors)) {
                    // 在父分类的祖级列表后面追加父分类 ID
                    category.setAncestors(parentAncestors + "," + parentId);
                } else {
                    // 父分类没有祖级列表，直接设置父分类 ID
                    category.setAncestors(String.valueOf(parentId));
                }
            } else {
                // 父分类不存在，设置为空
                category.setAncestors("");
            }
        }
    }
}
