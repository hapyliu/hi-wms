package com.haifeng.basedata.mapper;

import com.haifeng.basedata.domain.BaseContainer;
import com.haifeng.common.extensions.mybatis.RootMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 容器Mapper接口
 * 
 * @author wangww
 * @date 2026-02-27
 */
@Mapper
public interface BaseContainerMapper extends RootMapper<BaseContainer>
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
     * 删除容器
     * 
     * @param id 容器主键
     * @return 结果
     */
    public int deleteBaseContainerById(Long id);

    /**
     * 批量删除容器
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseContainerByIds(Long[] ids);

    /**
     * 根据容器编号列表查询容器
     *
     * @param containerCodes 容器编号列表
     * @return 容器集合
     */
    public List<BaseContainer> selectBaseContainerListByCodes(List<String> containerCodes);

    /**
     * 批量新增容器
     * 
     * @param baseContainers 容器列表
     * @return 结果
     */
    public int batchInsertBaseContainer(List<BaseContainer> baseContainers);

    /**
     * 查询容器
     *
     * @param containerCode 容器编号
     * @return 容器
     */
    BaseContainer  selectBaseContainerByCode(String containerCode);
}
