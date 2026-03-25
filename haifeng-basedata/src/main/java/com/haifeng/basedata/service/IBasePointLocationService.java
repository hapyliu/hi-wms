package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BasePointLocation;
import java.util.List;

public interface IBasePointLocationService {
    /**
     * 新增
     */
    void insertPointLocation(BasePointLocation basePointLocation);

    /**
     * 删除
     */
    void deletePointLocation(Long[] ids);

    /**
     * 查询详情
     */
    BasePointLocation selectPointLocationById(Long id);

    /**
     * 查询列表
     */
    List<BasePointLocation> selectPointLocationList(BasePointLocation basePointLocation);

    /**
     * 根据点位ID查询数量
     */
    Long countByPointId(Long pointId);
    /**
     * 根据库位ID删除关联
     */
    void deleteByLocationId(Long locationId);
}
