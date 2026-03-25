package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseMaterialCategory;
import com.haifeng.basedata.service.IBaseMaterialCategoryService;
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
@RequestMapping("/basedata/materialCategory")
@Tag(name = "物料分类管理")
public class BaseMaterialCategoryController extends BaseController {

    @Autowired
    private IBaseMaterialCategoryService categoryService;

    /**
     * 查询物料分类列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:materialCategory:list')")
    @GetMapping("/list")
    @Operation(summary = "查询物料分类列表")
    public TableDataInfo list(BaseMaterialCategory category) {
        startPage();
        List<BaseMaterialCategory> list = categoryService.selectCategoryList(category);
        return getDataTable(list);
    }

    /**
     * 导出物料分类列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:materialCategory:export')")
    @Log(title = "物料分类", businessType = BusinessTypeEnum.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出物料分类列表")
    public void export(HttpServletResponse response, BaseMaterialCategory category) {
        List<BaseMaterialCategory> list = categoryService.selectCategoryList(category);
        ExcelUtil<BaseMaterialCategory> util = new ExcelUtil<BaseMaterialCategory>(BaseMaterialCategory.class);
        util.exportExcel(response, list, MessageUtils.message("material.category.export.sheet.name"));
    }

    /**
     * 获取物料分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:materialCategory:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询物料分类详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(categoryService.selectCategoryById(id));
    }

    /**
     * 新增物料分类
     */
    @PreAuthorize("@ss.hasPermi('basedata:materialCategory:add')")
    @PostMapping
    @Operation(summary = "新增物料分类")
    @Log(title = "物料分类", businessType = BusinessTypeEnum.INSERT)
    public AjaxResult add(@Validated @RequestBody BaseMaterialCategory category) {
        categoryService.insertCategory(category);
        return AjaxResult.success();
    }

    /**
     * 修改物料分类
     */
    @PreAuthorize("@ss.hasPermi('basedata:materialCategory:edit')")
    @PutMapping
    @Operation(summary = "修改物料分类")
    @Log(title = "物料分类", businessType = BusinessTypeEnum.UPDATE)
    public AjaxResult edit(@Validated @RequestBody BaseMaterialCategory category) {
        categoryService.updateCategory(category);
        return AjaxResult.success();
    }

    /**
     * 删除物料分类
     */
    @PreAuthorize("@ss.hasPermi('basedata:materialCategory:remove')")
    @DeleteMapping("/{id}")
    @Operation(summary = "删除物料分类")
    @Log(title = "物料分类", businessType = BusinessTypeEnum.DELETE)
    public AjaxResult remove(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return AjaxResult.success();
    }
}
