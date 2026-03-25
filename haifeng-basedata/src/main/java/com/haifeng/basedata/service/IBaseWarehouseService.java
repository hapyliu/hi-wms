package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseWarehouse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【base_warehouse(仓库表)】的数据库操作 Service
* @createDate 2026-03-04 14:30:00
*/
public interface IBaseWarehouseService extends IService<BaseWarehouse> {

    /**
     * 查询仓库列表
     */
    List<BaseWarehouse> selectBaseWarehouseList(BaseWarehouse baseWarehouse);


    /**
     * 修改仓库
     * @param baseWarehouse
     */
    void updateBaseWarehouse(BaseWarehouse baseWarehouse);

    /**
     * 新增仓库
     * @param baseWarehouse
     */
    void addBaseWarehouse(BaseWarehouse baseWarehouse);

    /**
     * 根据编号查询仓库
     * @param warehouseCode
     * @return
     */
    BaseWarehouse getByCode(String warehouseCode);

    /**
     * 根据仓库 ID 查询关联的库区数量
     * @param warehouseId 仓库 ID
     * @return 关联的库区数量
     */
    int countAreasByWarehouseId(Long warehouseId);

    /**
     * 删除仓库
     * @param ids 需要删除的仓库 ID 数组
     */
    void deleteBaseWarehouse(Long[] ids);
}
