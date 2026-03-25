package com.haifeng.basedata.mapper;

import com.haifeng.basedata.domain.BaseWarehouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【base_warehouse(仓库表)】的数据库操作 Mapper
* @createDate 2026-03-04 14:30:00
* @Entity com.haifeng.basedata.domain.BaseWarehouse
*/
@Mapper
public interface BaseWarehouseMapper extends BaseMapper<BaseWarehouse> {

}
