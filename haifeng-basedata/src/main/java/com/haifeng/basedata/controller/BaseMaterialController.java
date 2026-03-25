package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseMaterial;
import com.haifeng.basedata.service.IBaseMaterialService;
import com.haifeng.common.annotation.Log;
import com.haifeng.common.core.controller.BaseController;
import com.haifeng.common.core.domain.AjaxResult;
import com.haifeng.common.core.page.TableDataInfo;
import com.haifeng.common.enums.BusinessTypeEnum;
import com.haifeng.common.utils.poi.ExcelUtil;
import com.haifeng.common.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basedata/material")
@Tag(name = "物料管理")
public class BaseMaterialController extends BaseController {

    @Autowired
    private IBaseMaterialService materialService;

    /**
     * 查询物料列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:material:list')")
    @GetMapping("/list")
    @Operation(summary = "查询物料列表")
    public TableDataInfo list(BaseMaterial material) {
        startPage();
        List<BaseMaterial> list = materialService.selectMaterialList(material);
        return getDataTable(list);
    }

    /**
     * 导出物料列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:material:export')")
    @Log(title = "物料", businessType = BusinessTypeEnum.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出物料列表")
    public void export(HttpServletResponse response, BaseMaterial material) {
        List<BaseMaterial> list = materialService.selectMaterialList(material);
        ExcelUtil<BaseMaterial> util = new ExcelUtil<BaseMaterial>(BaseMaterial.class);
        util.exportExcel(response, list, MessageUtils.message("material.export.sheet.name"));
    }

    /**
     * 获取物料详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:material:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询物料详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(materialService.selectMaterialById(id));
    }

    /**
     * 新增物料
     */
    @PreAuthorize("@ss.hasPermi('basedata:material:add')")
    @PostMapping
    @Operation(summary = "新增物料")
    @Log(title = "物料", businessType = BusinessTypeEnum.INSERT)
    public AjaxResult add(@Validated @RequestBody BaseMaterial material) {
        materialService.insertMaterial(material);
        return AjaxResult.success();
    }

    /**
     * 修改物料
     */
    @PreAuthorize("@ss.hasPermi('basedata:material:edit')")
    @PutMapping
    @Operation(summary = "修改物料")
    @Log(title = "物料", businessType = BusinessTypeEnum.UPDATE)
    public AjaxResult edit(@Validated @RequestBody BaseMaterial material) {
        materialService.updateMaterial(material);
        return AjaxResult.success();
    }

    /**
     * 删除物料
     */
    @PreAuthorize("@ss.hasPermi('basedata:material:remove')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除物料")
    @Log(title = "物料", businessType = BusinessTypeEnum.DELETE)
    public AjaxResult remove(@PathVariable Long[] ids) {
        materialService.deleteMaterial(ids);
        return AjaxResult.success();
    }
}
