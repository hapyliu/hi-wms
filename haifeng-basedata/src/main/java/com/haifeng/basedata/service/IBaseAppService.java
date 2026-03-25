package com.haifeng.basedata.service;

import com.haifeng.basedata.domain.BaseApp;

import java.util.List;

/**
 * 应用管理服务接口
 *
 * @author haifeng
 */
public interface IBaseAppService {

    /**
     * 新增应用信息
     *
     * @param baseApp 应用信息对象
     */
    void insertApp(BaseApp baseApp);

    /**
     * 修改应用信息
     *
     * @param baseApp 应用信息对象
     */
    void updateApp(BaseApp baseApp);

    /**
     * 删除应用信息
     *
     * @param ids 需要删除的应用ID数组
     */
    void deleteApp(Long[] ids);

    /**
     * 根据ID查询应用信息
     *
     * @param id 应用ID
     * @return 应用信息对象
     */
    BaseApp selectAppById(Long id);

    /**
     * 查询应用信息列表
     *
     * @param baseApp 应用信息查询条件
     * @return 应用信息列表
     */
    List<BaseApp> selectAppList(BaseApp baseApp);
}
