package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseContainerModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【base_container_model(容器模型表)】的数据库操作Service
* @createDate 2026-02-28 09:45:53
*/
public interface IBaseContainerModelService extends IService<BaseContainerModel> {

    /**
     * 查询容器模型列表
     */
    List<BaseContainerModel> selectBaseContainerModelList(BaseContainerModel baseContainerModel);


    /**
     * 修改容器模型
     * @param baseContainerModel
     */
    int updateBaseContainerModel(BaseContainerModel baseContainerModel);

    /**
     * 新增容器模型
     * @param baseContainerModel
     */
    void addBaseContainerModel(BaseContainerModel baseContainerModel);

    /**
     * 根据编号查询容器模型
     * @param containerModelCode
     * @return
     */
    BaseContainerModel getByCode(String containerModelCode);

    /**
     * 删除容器模型
     * @param ids 容器模型 id
     * @return
     */
    int removeContainerModel(List<Long> ids);
}
