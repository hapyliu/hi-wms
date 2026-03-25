package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BasePoint;

import java.util.List;

/**
 * 点位管理服务接口
 *
 * @author haifeng
 */
public interface IBasePointService {

    /**
     * 新增点位信息
     *
     * @param BasePoint 点位信息对象
     * @return 插入成功的记录ID
     */
    void insertPoint(BasePoint BasePoint);

    /**
     * 修改点位信息
     *
     * @param BasePoint 点位信息对象
     */
    void updatePoint(BasePoint BasePoint);

    /**
     * 删除点位信息
     *
     * @param ids 需要删除的点位ID数组
     */
    void deletePoint(Long[] ids);

    /**
     * 根据ID查询点位信息
     *
     * @param id 点位ID
     * @return 点位信息对象
     */
    BasePoint selectPointById(Long id);

    /**
     * 查询点位信息列表
     *
     * @param BasePoint 点位信息查询条件
     * @return 点位信息列表
     */
    List<BasePoint> selectPointList(BasePoint BasePoint);

    /**
     * 导入点位数据
     *
     * @param pointList     点位数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName      操作用户
     * @return 结果
     */
    String importPoint(List<BasePoint> pointList, boolean updateSupport, String operName);
}
