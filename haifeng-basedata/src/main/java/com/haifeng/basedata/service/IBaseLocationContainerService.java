package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseLocationContainer;
import java.util.List;

public interface IBaseLocationContainerService {
    /**
     * 新增
     */
    void insertLocationContainer(BaseLocationContainer baseLocationContainer);

    /**
     * 删除
     */
    void deleteLocationContainer(BaseLocationContainer baseLocationContainer);

    /**
     * 查询列表
     */
    List<BaseLocationContainer> selectLocationContainerList(BaseLocationContainer baseLocationContainer);
}
