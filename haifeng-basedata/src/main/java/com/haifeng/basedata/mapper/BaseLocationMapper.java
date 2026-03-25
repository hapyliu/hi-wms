package com.haifeng.basedata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haifeng.basedata.domain.BaseLocation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseLocationMapper extends BaseMapper<BaseLocation> {
    /**
     * 查询库位列表（支持关联工作站和库区搜索）
     */
    java.util.List<BaseLocation> selectLocationList(@org.apache.ibatis.annotations.Param("location") BaseLocation location);
}
