package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseLocationStation;

public interface IBaseLocationStationService {

    /**
     * 新增库位工作站关联
     */
    void insertLocationStation(BaseLocationStation baseLocationStation);

    /**
     * 删除库位工作站关联
     */
    void deleteLocationStation(BaseLocationStation baseLocationStation);

    /**
     * 查询库位工作站关联列表
     */
    java.util.List<BaseLocationStation> selectLocationStationList(BaseLocationStation baseLocationStation);
}
