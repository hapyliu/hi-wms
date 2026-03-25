package com.haifeng.basedata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haifeng.basedata.domain.BaseMaterial;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BaseMaterialMapper extends BaseMapper<BaseMaterial> {
    
    /**
     * 关联查询物料列表（包含物料分类名称）
     * @param material 物料查询条件
     * @return 物料列表
     */
    List<BaseMaterial> selectMaterialListWithCategory(BaseMaterial material);
}
