package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseLocation;
import com.haifeng.basedata.dto.BaseLocationOprDTO;

import java.util.List;

public interface IBaseLocationService {
    void insertLocation(BaseLocation location);

    void updateLocation(BaseLocation location);

    void deleteLocation(Long[] ids);

    BaseLocation selectLocationById(Long id);

    List<BaseLocation> selectLocationList(BaseLocation location);

    /**
     * 库位统一操作
     */
    void locationOpr(BaseLocationOprDTO opr);
}
