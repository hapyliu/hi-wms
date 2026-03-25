package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BasePointStation;

public interface IBasePointStationService {

    /**
     * 新增点位工作站关联
     */
    void insertPointStation(BasePointStation basePointStation);

    /**
     * 删除点位工作站关联
     */
    void deletePointStation(BasePointStation basePointStation);
    /**
     * 根据点位编码找工作站编码
     */
    BasePointStation selectByPointId(Long pointId);
}
