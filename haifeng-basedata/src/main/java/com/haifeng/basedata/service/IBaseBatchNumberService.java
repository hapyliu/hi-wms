package com.haifeng.basedata.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haifeng.basedata.domain.BaseBatchNumber;
import com.haifeng.basedata.domain.request.BatchNumberCreateRequest;
import com.haifeng.basedata.domain.vo.BatchNumberVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【base_batch_number(批次号表)】的数据库操作 Service
* @createDate 2026-03-11 09:11:46
*/
public interface IBaseBatchNumberService extends IService<BaseBatchNumber> {

    /**
     * 创建批次号
     * @param request 创建请求
     * @return 创建的批次号信息
     */
    void createBatchNumber(BatchNumberCreateRequest request);

    /**
     * 查询批次号列表
     * @param baseBatchNumber 查询条件
     * @return 批次号列表
     */
    List<BaseBatchNumber> selectBaseBatchNumberList(BaseBatchNumber baseBatchNumber);

    /**
     * 查询批次号列表（分页）
     * @param baseBatchNumber 查询条件
     * @return 分页结果
     */
    Page<BatchNumberVo> selectBaseBatchNumberPage(BaseBatchNumber baseBatchNumber);

    /**
     * 根据 ID 查询批次号详细信息
     * @param id 批次号 ID
     * @return 批次号详细信息
     */
    BatchNumberVo getBatchNumberById(Long id);

    /**
     * 转化为响应列表
     * @param list
     * @return
     */
    List<BatchNumberVo> convertToResponseList(List<BaseBatchNumber> list);

}
