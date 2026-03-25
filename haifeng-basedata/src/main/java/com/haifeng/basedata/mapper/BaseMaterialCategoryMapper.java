package com.haifeng.basedata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haifeng.basedata.domain.BaseMaterial;
import com.haifeng.basedata.domain.BaseMaterialCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BaseMaterialCategoryMapper extends BaseMapper<BaseMaterialCategory> {
    List<BaseMaterialCategory> selectChildrenCategoryById(Long id);

    void updateCategoryChildren(List<BaseMaterialCategory> children);

    /**
     * 查询分类下的物料列表（包括子孙分类）
     * @param categoryId 分类 ID
     * @return 物料列表
     */
    List<BaseMaterial> selectMaterialsByCategoryId(Long categoryId);
}
