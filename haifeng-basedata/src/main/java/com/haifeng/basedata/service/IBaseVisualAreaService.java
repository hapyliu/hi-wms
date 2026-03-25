package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseVisualArea;

import java.util.List;

/**
 * 可视化区域管理服务接口
 *
 * @author haifeng
 */
public interface IBaseVisualAreaService {

    /**
     * 新增可视化区域信息
     *
     * @param baseVisualArea 可视化区域信息对象
     */
    void insertVisualArea(BaseVisualArea baseVisualArea);

    /**
     * 修改可视化区域信息
     *
     * @param baseVisualArea 可视化区域信息对象
     */
    void updateVisualArea(BaseVisualArea baseVisualArea);

    /**
     * 删除可视化区域信息
     *
     * @param ids 需要删除的可视化区域ID数组
     */
    void deleteVisualArea(Long[] ids);

    /**
     * 根据ID查询可视化区域信息
     *
     * @param id 可视化区域ID
     * @return 可视化区域信息对象
     */
    BaseVisualArea selectVisualAreaById(Long id);

    /**
     * 查询可视化区域信息列表
     *
     * @param baseVisualArea 可视化区域信息查询条件
     * @return 可视化区域信息列表
     */
    List<BaseVisualArea> selectVisualAreaList(BaseVisualArea baseVisualArea);
}
