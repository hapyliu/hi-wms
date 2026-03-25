package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseContainerModel;
import com.haifeng.basedata.service.IBaseContainerModelService;
import com.haifeng.common.annotation.Log;
import com.haifeng.common.core.controller.BaseController;
import com.haifeng.common.core.domain.AjaxResult;
import com.haifeng.common.core.page.TableDataInfo;
import com.haifeng.common.enums.BusinessTypeEnum;
import com.haifeng.common.group.AddGroup;
import com.haifeng.common.group.UpdateGroup;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.poi.ExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 容器模型 Controller
 * @author wangww
 * @version 1.0
 * @description TODO
 * @date 2026-02-28 13:57:26
 */
@RestController
@RequestMapping("/basedata/containerModel")
@Tag(name = "容器模型管理")
public class BaseContainerModelController extends BaseController {

    @Autowired
    private IBaseContainerModelService baseContainerModelService;

    /**
     * 查询容器模型列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:containerModel:list')")
    @GetMapping("/list")
    @Operation(summary = "查询容器模型列表")
    public TableDataInfo list(BaseContainerModel baseContainerModel) {
        startPage();
        List<BaseContainerModel> list = baseContainerModelService.selectBaseContainerModelList(baseContainerModel);
        return getDataTable(list);
    }

    /**
     * 查询容器模型列表(不分页)
     */
    @PreAuthorize("@ss.hasPermi('basedata:containerModel:list')")
    @GetMapping("/queryList")
    @Operation(summary = "查询容器模型列表(不分页)")
    public AjaxResult queryList(BaseContainerModel baseContainerModel) {
        List<BaseContainerModel> list = baseContainerModelService.selectBaseContainerModelList(baseContainerModel);
        return AjaxResult.success(list);
    }

    /**
     * 导出容器模型列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:containerModel:export')")
    @PostMapping("/export")
    @Operation(summary = "导出容器模型列表")
    public void export(HttpServletResponse response, BaseContainerModel baseContainerModel) {
        List<BaseContainerModel> list = baseContainerModelService.selectBaseContainerModelList(baseContainerModel);
        ExcelUtil<BaseContainerModel> util = new ExcelUtil<BaseContainerModel>(BaseContainerModel.class);
        util.exportExcel(response, list, MessageUtils.message("container.export.sheet.name"));
    }

    /**
     * 获取容器模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:containerModel:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "获取容器模型详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(baseContainerModelService.getById(id));
    }

    /**
     * 新增容器模型
     */
    @PreAuthorize("@ss.hasPermi('basedata:containerModel:add')")
    @Log(title = "容器模型", businessType = BusinessTypeEnum.INSERT)
    @PostMapping
    @Operation(summary = "新增容器模型")
    public AjaxResult add(@Validated(AddGroup.class) @RequestBody BaseContainerModel baseContainerModel) {
        baseContainerModelService.addBaseContainerModel(baseContainerModel);
        return AjaxResult.success();
    }

    /**
     * 修改容器模型
     */
    @PreAuthorize("@ss.hasPermi('basedata:containerModel:edit')")
    @Log(title = "容器模型", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping
    @Operation(summary = "修改容器模型")
    public AjaxResult edit(@Validated(UpdateGroup.class) @RequestBody BaseContainerModel baseContainerModel) {
        return toAjax(baseContainerModelService.updateBaseContainerModel(baseContainerModel));
    }

    /**
     * 删除容器模型
     */
    @PreAuthorize("@ss.hasPermi('basedata:containerModel:remove')")
    @Log(title = "容器模型", businessType = BusinessTypeEnum.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除容器模型")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(baseContainerModelService.removeContainerModel(Arrays.asList(ids)));
    }


}
