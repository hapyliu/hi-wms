package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseWarehouse;
import com.haifeng.basedata.service.IBaseWarehouseService;
import com.haifeng.common.annotation.Log;
import com.haifeng.common.core.controller.BaseController;
import com.haifeng.common.core.domain.AjaxResult;
import com.haifeng.common.core.page.TableDataInfo;
import com.haifeng.common.enums.BusinessTypeEnum;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.poi.ExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 仓库 Controller
 * @author wangww
 * @version 1.0
 * @description TODO
 * @date 2026-03-04 14:30:00
 */
@RestController
@RequestMapping("/basedata/warehouse")
@Tag(name = "仓库管理")
public class BaseWarehouseController extends BaseController {

    @Autowired
    private IBaseWarehouseService baseWarehouseService;

    /**
     * 查询仓库列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:warehouse:list')")
    @GetMapping("/list")
    @Operation(summary = "查询仓库列表")
    public TableDataInfo list(BaseWarehouse baseWarehouse) {
        startPage();
        List<BaseWarehouse> list = baseWarehouseService.selectBaseWarehouseList(baseWarehouse);
        return getDataTable(list);
    }

    /**
     * 导出仓库列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:warehouse:export')")
    @PostMapping("/export")
    @Operation(summary = "导出仓库列表")
    public void export(HttpServletResponse response, BaseWarehouse baseWarehouse) {
        List<BaseWarehouse> list = baseWarehouseService.selectBaseWarehouseList(baseWarehouse);
        ExcelUtil<BaseWarehouse> util = new ExcelUtil<BaseWarehouse>(BaseWarehouse.class);
        util.exportExcel(response, list, MessageUtils.message("warehouse.export.sheet.name"));
    }

    /**
     * 获取仓库详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:warehouse:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "获取仓库详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(baseWarehouseService.getById(id));
    }

    /**
     * 新增仓库
     */
    @PreAuthorize("@ss.hasPermi('basedata:warehouse:add')")
    @Log(title = "仓库", businessType = BusinessTypeEnum.INSERT)
    @PostMapping
    @Operation(summary = "新增仓库")
    public AjaxResult add(@Validated @RequestBody BaseWarehouse baseWarehouse) {
        baseWarehouseService.addBaseWarehouse(baseWarehouse);
        return AjaxResult.success();
    }

    /**
     * 修改仓库
     */
    @PreAuthorize("@ss.hasPermi('basedata:warehouse:edit')")
    @Log(title = "仓库", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping
    @Operation(summary = "修改仓库")
    public AjaxResult edit(@Validated @RequestBody BaseWarehouse baseWarehouse) {
        baseWarehouseService.updateBaseWarehouse(baseWarehouse);
        return AjaxResult.success();
    }

    /**
     * 删除仓库
     */
    @PreAuthorize("@ss.hasPermi('basedata:warehouse:remove')")
    @Log(title = "仓库", businessType = BusinessTypeEnum.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除仓库")
    public AjaxResult remove(@PathVariable Long[] ids) {
        baseWarehouseService.deleteBaseWarehouse(ids);
        return AjaxResult.success();
    }


}
