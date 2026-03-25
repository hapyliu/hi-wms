package com.haifeng.basedata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haifeng.basedata.domain.BaseBatchAttribute;
import com.haifeng.basedata.domain.request.EnableOrDisableRequest;

import java.util.List;

/**
* @author Administrator
* @description 针对表【base_batch_attribute(批次属性表)】的数据库操作 Service
* @createDate 2026-03-11 09:11:46
*/
public interface IBaseBatchAttributeService extends IService<BaseBatchAttribute> {

    /**
     * 查询批次属性列表
     * @param baseBatchAttribute 批次属性查询条件
     * @return 批次属性列表
     */
    List<BaseBatchAttribute> selectBaseBatchAttributeList(BaseBatchAttribute baseBatchAttribute);

    /**
     * 新增批次属性
     * @param baseBatchAttribute 批次属性信息
     * @return 结果
     */
    boolean insertBaseBatchAttribute(BaseBatchAttribute baseBatchAttribute);

    /**
     * 修改批次属性
     * @param baseBatchAttribute 批次属性信息
     * @return 结果
     */
    boolean updateBaseBatchAttribute(BaseBatchAttribute baseBatchAttribute);

    /**
     * 删除批次属性
     * @param ids 需要删除的 ID 数组
     * @return 结果
     */
    int deleteBaseBatchAttributeByIds(Long[] ids);

    /**
     * 校验批次属性编码是否唯一
     * @param baseBatchAttribute 批次属性信息
     * @return true-不重复，false-重复
     */
    boolean checkAttrCodeUnique(BaseBatchAttribute baseBatchAttribute);

    /**
     * 校验批次属性顺序是否唯一
     * @param baseBatchAttribute 批次属性信息
     * @return true-不重复，false-重复
     */
    boolean checkAttrIndexUnique(BaseBatchAttribute baseBatchAttribute);

    /**
     * 启用/停用批次属性
     * @param enableOrDisableRequest 启用/停用批次属性请求参数
     * @return 启用/停用结果
     */
    int changeStatus(EnableOrDisableRequest enableOrDisableRequest);
}
