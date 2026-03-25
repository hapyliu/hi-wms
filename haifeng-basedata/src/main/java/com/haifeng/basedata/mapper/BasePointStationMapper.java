package com.haifeng.basedata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haifeng.basedata.domain.BasePointStation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BasePointStationMapper extends BaseMapper<BasePointStation> {
    BasePointStation selectStationCodeByPointId(Long pointId);
}
