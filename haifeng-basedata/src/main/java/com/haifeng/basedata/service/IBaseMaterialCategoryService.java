package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseMaterialCategory;

import java.util.List;

/**
 * 物料分类管理服务接口
 *
 * @author haifeng
 */
public interface IBaseMaterialCategoryService {

    /**
     * 新增物料分类信息
     *
     * @param category 物料分类信息对象
     */
    void insertCategory(BaseMaterialCategory category);

    /**
     * 修改物料分类信息
     *
     * @param category 物料分类信息对象
     */
    void updateCategory(BaseMaterialCategory category);

    /**
     * 删除物料分类信息
     *
     * @param id 需要删除的物料分类ID
     */
    void deleteCategory(Long id);

    /**
     * 根据ID查询物料分类信息
     *
     * @param id 物料分类ID
     * @return 物料分类信息对象
     */
    BaseMaterialCategory selectCategoryById(Long id);

    /**
     * 查询物料分类信息列表
     *
     * @param category 物料分类信息查询条件
     * @return 物料分类信息列表
     */
    List<BaseMaterialCategory> selectCategoryList(BaseMaterialCategory category);

    /**
     * 获取指定分类下的所有分类id，包含它的所有子孙分类
     *
     * @param parentId 父分类id
     * @return 分类id集合
     */
    List<Long> selectAllCategoryIdsByParentId(Long parentId);

    /**
     * 根据分类id查询分类是否存在
     *
     * @param categoryId 分类id
     * @return true-存在 false-不存在
     */
    boolean checkCategoryIdExists(Long categoryId);
}
