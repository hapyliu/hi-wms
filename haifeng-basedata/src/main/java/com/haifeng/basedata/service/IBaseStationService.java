package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseStation;

import java.util.List;

public interface IBaseStationService {
    /**
     * 新增工作站信息
     *
     * @param BaseStation 工作站信息对象
     */
    void insertStation(BaseStation BaseStation);

    /**
     * 修改工作站信息
     *
     * @param BaseStation 工作站信息对象
     */
    void updateStation(BaseStation BaseStation);

    /**
     * 删除工作站信息
     *
     * @param ids 需要删除的工作站ID数组
     */
    void deleteStation(Long[] ids);

    /**
     * 根据ID查询工作站信息
     *
     * @param id 工作站ID
     * @return 工作站信息对象
     */
    BaseStation selectStationById(Long id);

    /**
     * 查询工作站信息列表
     *
     * @param BaseStation 工作站信息查询条件
     * @return 工作站信息列表
     */
    List<BaseStation> selectStationList(BaseStation BaseStation);
}
