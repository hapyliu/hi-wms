package com.haifeng.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haifeng.basedata.domain.BaseArea;
import com.haifeng.basedata.domain.BaseWarehouse;
import com.haifeng.basedata.mapper.BaseAreaMapper;
import com.haifeng.basedata.mapper.BaseWarehouseMapper;
import com.haifeng.basedata.service.IBaseWarehouseService;
import com.haifeng.common.constant.UserConstants;
import com.haifeng.common.exception.ServiceException;
import com.haifeng.common.utils.MessageUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【base_warehouse(仓库表)】的数据库操作 Service 实现
* @createDate 2026-03-04 14:30:00
*/
@Service
public class BaseWarehouseServiceImpl extends ServiceImpl<BaseWarehouseMapper, BaseWarehouse>
    implements IBaseWarehouseService {

    @Autowired
    private BaseWarehouseMapper baseWarehouseMapper;

    @Autowired
    private BaseAreaMapper baseAreaMapper;

    @Override
    public List<BaseWarehouse> selectBaseWarehouseList(BaseWarehouse baseWarehouse) {
        LambdaQueryWrapper<BaseWarehouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(baseWarehouse.getWarehouseName()), BaseWarehouse::getWarehouseName, baseWarehouse.getWarehouseName())
                .like(StringUtils.isNotBlank(baseWarehouse.getWarehouseCode()), BaseWarehouse::getWarehouseCode, baseWarehouse.getWarehouseCode());
        return baseWarehouseMapper.selectList(queryWrapper);
    }

    @Override
    public void updateBaseWarehouse(BaseWarehouse baseWarehouse) {
        if (baseWarehouse.getId() ==  null) {
            throw new ServiceException(MessageUtils.message("update.warehouse.id.required"));
        }
        // 校验是否有关联的库区，如果有则禁止修改仓库编号
        checkAssociatedAreaForEdit(baseWarehouse);
        if (!checkWarehouseCodeUnique(baseWarehouse)) {
            throw new ServiceException(MessageUtils.message("warehouse.code.already.exists"));
        }
        baseWarehouseMapper.updateById(baseWarehouse);
    }

    @Override
    public void addBaseWarehouse(BaseWarehouse baseWarehouse) {
        if (!checkWarehouseCodeUnique(baseWarehouse)) {
            throw new ServiceException(MessageUtils.message("warehouse.code.already.exists"));
        }
        baseWarehouseMapper.insert(baseWarehouse);
    }

    @Override
    public BaseWarehouse getByCode(String warehouseCode) {
        LambdaQueryWrapper<BaseWarehouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseWarehouse::getWarehouseCode, warehouseCode);
        return baseWarehouseMapper.selectOne(queryWrapper);
    }

    private boolean checkWarehouseCodeUnique(BaseWarehouse baseWarehouse) {
        LambdaQueryWrapper<BaseWarehouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseWarehouse::getWarehouseCode, baseWarehouse.getWarehouseCode());
        List<BaseWarehouse> baseWarehouseList = baseWarehouseMapper.selectList(queryWrapper);
        baseWarehouseList = baseWarehouseList.stream().filter(b -> !b.getId().equals(baseWarehouse.getId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(baseWarehouseList)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int countAreasByWarehouseId(Long warehouseId) {
        // 先根据仓库 ID 查询仓库编号
        BaseWarehouse warehouse = baseWarehouseMapper.selectById(warehouseId);
        if (warehouse == null) {
            return 0;
        }
        // 根据仓库编号查询关联的库区数量
        LambdaQueryWrapper<BaseArea> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseArea::getWarehouseCode, warehouse.getWarehouseCode());
        return Math.toIntExact(baseAreaMapper.selectCount(queryWrapper));
    }

    /**
     * 删除时检查是否有关联库区
     * @param warehouseId 仓库 ID
     */
    private void checkAssociatedAreaForDelete(Long warehouseId) {
        int count = countAreasByWarehouseId(warehouseId);
        if (count > 0) {
            throw new ServiceException(MessageUtils.message("warehouse.has.area.cannot.delete"));
        }
    }

    /**
     * 编辑时检查是否有关联库区，如果有则禁止修改仓库编号
     * @param baseWarehouse 仓库对象
     */
    private void checkAssociatedAreaForEdit(BaseWarehouse baseWarehouse) {
        int count = countAreasByWarehouseId(baseWarehouse.getId());
        if (count > 0) {
            // 如果有关联库区，检查是否修改了仓库编号
            BaseWarehouse oldWarehouse = baseWarehouseMapper.selectById(baseWarehouse.getId());
            if (oldWarehouse != null && !oldWarehouse.getWarehouseCode().equals(baseWarehouse.getWarehouseCode())) {
                throw new ServiceException(MessageUtils.message("warehouse.has.area.cannot.change.code"));
            }
        }
    }

    @Override
    public void deleteBaseWarehouse(Long[] ids) {
        // 校验每个仓库是否有关联的库区
        for (Long id : ids) {
            checkAssociatedAreaForDelete(id);
        }
        baseWarehouseMapper.deleteBatchIds(Arrays.asList(ids));
    }
}
