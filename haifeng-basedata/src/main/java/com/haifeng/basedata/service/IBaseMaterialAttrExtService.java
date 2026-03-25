package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseMaterialAttrExt;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【base_material_attr_ext(物料属性扩展表)】的数据库操作 Service
 * @createDate 2026-03-11 09:11:45
 */
public interface IBaseMaterialAttrExtService extends IService<BaseMaterialAttrExt> {

    /**
     * 根据物料 ID 查询属性扩展列表
     * @param materialId 物料 ID
     * @return 物料属性扩展列表
     */
    List<BaseMaterialAttrExt> selectByMaterialId(Long materialId);

    /**
     * 新增物料属性扩展
     * @param baseMaterialAttrExt 物料属性扩展信息
     * @return 结果
     */
    boolean insertBaseMaterialAttrExt(BaseMaterialAttrExt baseMaterialAttrExt);

    /**
     * 修改物料属性扩展
     * @param baseMaterialAttrExt 物料属性扩展信息
     * @return 结果
     */
    boolean updateBaseMaterialAttrExt(BaseMaterialAttrExt baseMaterialAttrExt);


    /**
     * 校验同一物料下属性名是否重复
     * @param baseMaterialAttrExt 物料属性扩展信息
     * @return true-不重复，false-重复
     */
    boolean checkAttrNameUnique(BaseMaterialAttrExt baseMaterialAttrExt);
}
