package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseArea;

import java.util.List;

/**
 * 工库区管理服务接口
 *
 * @author haifeng
 */
public interface IBaseAreaService {

    /**
     * 新增工库区信息
     *
     * @param baseArea 工库区信息对象
     */
    void insertArea(BaseArea baseArea);

    /**
     * 修改工库区信息
     *
     * @param baseArea 工库区信息对象
     */
    void updateArea(BaseArea baseArea);

    /**
     * 删除工库区信息
     *
     * @param ids 需要删除的工库区ID数组
     */
    void deleteArea(Long[] ids);

    /**
     * 根据ID查询工库区信息
     *
     * @param id 工库区ID
     * @return 工库区信息对象
     */
    BaseArea selectAreaById(Long id);

    /**
     * 查询工库区信息列表
     *
     * @param baseArea 工库区信息查询条件
     * @return 工库区信息列表
     */
    List<BaseArea> selectAreaList(BaseArea baseArea);
}
