package com.haifeng.basedata.controller;

import com.github.pagehelper.PageInfo;
import com.haifeng.basedata.domain.BaseBatchNumber;
import com.haifeng.basedata.domain.request.BatchNumberCreateRequest;
import com.haifeng.basedata.domain.vo.BatchNumberVo;
import com.haifeng.basedata.service.IBaseBatchNumberService;
import com.haifeng.common.annotation.Log;
import com.haifeng.common.core.controller.BaseController;
import com.haifeng.common.core.domain.AjaxResult;
import com.haifeng.common.core.page.TableDataInfo;
import com.haifeng.common.enums.BusinessTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 批次号管理 Controller
 * 
 * @author Administrator
 * @date 2026-03-11
 */
@RestController
@RequestMapping("/basedata/batchNumber")
@Tag(name = "批次号管理")
public class BaseBatchNumberController extends BaseController {

    @Autowired
    private IBaseBatchNumberService baseBatchNumberService;

    /**
     * 查询批次号列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:batchNumber:list')")
    @GetMapping("/list")
    @Operation(summary = "查询批次号列表")
    public TableDataInfo list(BaseBatchNumber baseBatchNumber) {
        startPage();
        List<BaseBatchNumber> baseBatchNumberList = baseBatchNumberService.selectBaseBatchNumberList(baseBatchNumber);
        List<BatchNumberVo> batchNumberVoList = baseBatchNumberService.convertToResponseList(baseBatchNumberList);
        return getDataTable(batchNumberVoList, new PageInfo(baseBatchNumberList).getTotal());
    }

    /**
     * 获取批次号详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:batchNumber:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询批次号详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(baseBatchNumberService.getBatchNumberById(id));
    }

    /**
     * 新增批次号
     */
    @PreAuthorize("@ss.hasPermi('basedata:batchNumber:add')")
    @Log(title = "批次号", businessType = BusinessTypeEnum.INSERT)
    @PostMapping
    @Operation(summary = "新增批次号")
    public AjaxResult add(@Validated @RequestBody BatchNumberCreateRequest request) {
        baseBatchNumberService.createBatchNumber(request);
        return AjaxResult.success();
    }
}
