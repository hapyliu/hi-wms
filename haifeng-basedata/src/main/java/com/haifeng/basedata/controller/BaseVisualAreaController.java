package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseVisualArea;
import com.haifeng.basedata.service.IBaseVisualAreaService;
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

@RestController
@RequestMapping("/basedata/visualArea")
@Tag(name = "可视化区域管理")
public class BaseVisualAreaController extends BaseController {

    @Autowired
    private IBaseVisualAreaService visualAreaService;

    /**
     * 查询可视化区域列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:visualArea:list')")
    @GetMapping("/list")
    @Operation(summary = "查询可视化区域分页列表")
    public TableDataInfo list(BaseVisualArea visualArea) {
        startPage();
        List<BaseVisualArea> list = visualAreaService.selectVisualAreaList(visualArea);
        return getDataTable(list);
    }

    /**
     * 获取可视化区域详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:visualArea:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询可视化区域详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(visualAreaService.selectVisualAreaById(id));
    }

    /**
     * 新增可视化区域
     */
    @PreAuthorize("@ss.hasPermi('basedata:visualArea:add')")
    @PostMapping
    @Operation(summary = "新增可视化区域")
    @Log(title = "可视化区域", businessType = BusinessTypeEnum.INSERT)
    public AjaxResult add(@Validated @RequestBody BaseVisualArea visualArea) {
        visualAreaService.insertVisualArea(visualArea);
        return AjaxResult.success();
    }

    /**
     * 修改可视化区域
     */
    @PreAuthorize("@ss.hasPermi('basedata:visualArea:edit')")
    @PutMapping
    @Operation(summary = "修改可视化区域")
    @Log(title = "可视化区域", businessType = BusinessTypeEnum.UPDATE)
    public AjaxResult edit(@Validated @RequestBody BaseVisualArea visualArea) {
        visualAreaService.updateVisualArea(visualArea);
        return AjaxResult.success();
    }

    /**
     * 删除可视化区域
     */
    @PreAuthorize("@ss.hasPermi('basedata:visualArea:remove')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除可视化区域")
    @Log(title = "可视化区域", businessType = BusinessTypeEnum.DELETE)
    public AjaxResult remove(@PathVariable Long[] ids) {
        visualAreaService.deleteVisualArea(ids);
        return AjaxResult.success();
    }
}
