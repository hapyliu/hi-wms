package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseMaterial;

import java.util.List;

/**
 * 物料管理服务接口
 *
 * @author haifeng
 */
public interface IBaseMaterialService {

    /**
     * 新增物料信息
     *
     * @param material 物料信息对象
     */
    void insertMaterial(BaseMaterial material);

    /**
     * 修改物料信息
     *
     * @param material 物料信息对象
     */
    void updateMaterial(BaseMaterial material);

    /**
     * 删除物料信息
     *
     * @param ids 需要删除的物料ID数组
     */
    void deleteMaterial(Long[] ids);

    /**
     * 根据ID查询物料信息
     *
     * @param id 物料ID
     * @return 物料信息对象
     */
    BaseMaterial selectMaterialById(Long id);

    /**
     * 查询物料信息列表
     *
     * @param material 物料信息查询条件
     * @return 物料信息列表
     */
    List<BaseMaterial> selectMaterialList(BaseMaterial material);

    /**
     * 根据父分类id查询下面的所有物料信息
     * 
     * @param parentId 父分类id
     * @return 物料列表
     */
    List<BaseMaterial> selectMaterialListByParentCategoryId(Long parentId);
}
