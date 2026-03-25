package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseArea;
import com.haifeng.basedata.service.IBaseAreaService;
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
@RequestMapping("/basedata/area")
@Tag(name = "库区管理")
public class BaseAreaController extends BaseController {

    @Autowired
    private IBaseAreaService areaService;

    /**
     * 查询库区列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:area:list')")
    @GetMapping("/list")
    @Operation(summary = "查询库区分页列表")
    public TableDataInfo list(BaseArea area) {
        startPage();
        List<BaseArea> list = areaService.selectAreaList(area);
        return getDataTable(list);
    }

    /**
     * 获取库区详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:area:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询库区详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(areaService.selectAreaById(id));
    }

    /**
     * 新增库区
     */
    @PreAuthorize("@ss.hasPermi('basedata:area:add')")
    @PostMapping
    @Operation(summary = "新增库区")
    @Log(title = "库区", businessType = BusinessTypeEnum.INSERT)
    public AjaxResult add(@Validated @RequestBody BaseArea area) {
        areaService.insertArea(area);
        return AjaxResult.success();
    }

    /**
     * 修改库区
     */
    @PreAuthorize("@ss.hasPermi('basedata:area:edit')")
    @PutMapping
    @Operation(summary = "修改库区")
    @Log(title = "库区", businessType = BusinessTypeEnum.UPDATE)
    public AjaxResult edit(@Validated @RequestBody BaseArea area) {
        areaService.updateArea(area);
        return AjaxResult.success();
    }

    /**
     * 删除库区
     */
    @PreAuthorize("@ss.hasPermi('basedata:area:remove')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除库区")
    @Log(title = "库区", businessType = BusinessTypeEnum.DELETE)
    public AjaxResult remove(@PathVariable Long[] ids) {
        areaService.deleteArea(ids);
        return AjaxResult.success();
    }
}
