package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseContainer;
import com.haifeng.basedata.domain.request.ContainerBatchCreateRequest;

import java.util.List;

/**
 * 容器Service接口
 * 
 * @author wangww
 * @date 2026-02-27
 */
public interface IBaseContainerService 
{
    /**
     * 查询容器
     * 
     * @param id 容器主键
     * @return 容器
     */
    public BaseContainer selectBaseContainerById(Long id);

    /**
     * 查询容器列表
     * 
     * @param baseContainer 容器
     * @return 容器集合
     */
    public List<BaseContainer> selectBaseContainerList(BaseContainer baseContainer);

    /**
     * 新增容器
     * 
     * @param baseContainer 容器
     * @return 结果
     */
    public int insertBaseContainer(BaseContainer baseContainer);

    /**
     * 修改容器
     * 
     * @param baseContainer 容器
     * @return 结果
     */
    public int updateBaseContainer(BaseContainer baseContainer);

    /**
     * 批量删除容器
     * 
     * @param ids 需要删除的容器主键集合
     * @return 结果
     */
    public int deleteBaseContainerByIds(Long[] ids);

    /**
     * 删除容器信息
     * 
     * @param id 容器主键
     * @return 结果
     */
    public int deleteBaseContainerById(Long id);

    /**
     * 校验容器编号是否唯一
     *
     * @param baseContainer 容器信息
     * @return 结果
     */
    boolean checkContainerCodeUnique(BaseContainer baseContainer);

    /**
     * 校验容器模型编号是否存在
     *
     * @param containerModelCode 容器模型编号
     * @return 结果
     */
    boolean checkContainerModelCodeExists(String containerModelCode);

    /**
     * 批量创建容器
     *
     * @param request 批量创建请求参数
     * @return 创建的容器数量
     */
    int batchCreateContainers(ContainerBatchCreateRequest request);

    /**
     * 根据容器编号查询容器信息
     * @param containerCode
     * @return
     */

    BaseContainer selectContainerByCode(String containerCode);

    /**
     * 根据容器编号编辑容器信息
     * @param baseContainer
     */
    void  updateContainerByCtrCode(BaseContainer baseContainer);
}
