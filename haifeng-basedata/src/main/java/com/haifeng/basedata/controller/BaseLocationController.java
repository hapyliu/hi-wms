package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseLocation;
import com.haifeng.basedata.dto.BaseLocationOprDTO;
import com.haifeng.basedata.service.IBaseLocationService;
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
@RequestMapping("/basedata/location")
@Tag(name = "库位管理")
public class BaseLocationController extends BaseController {
    @Autowired
    private IBaseLocationService locationService;

    @PreAuthorize("@ss.hasPermi('basedata:location:list')")
    @GetMapping("/list")
    @Operation(summary = "库位分页列表")
    public TableDataInfo list(BaseLocation location) {
        startPage();
        List<BaseLocation> list = locationService.selectLocationList(location);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('basedata:location:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "库位详情")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(locationService.selectLocationById(id));
    }

    @PreAuthorize("@ss.hasPermi('basedata:location:add')")
    @PostMapping
    @Operation(summary = "新增库位")
    @Log(title = "库位", businessType = BusinessTypeEnum.INSERT)
    public AjaxResult add(@Validated @RequestBody BaseLocation location) {
        locationService.insertLocation(location);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('basedata:location:edit')")
    @PutMapping
    @Operation(summary = "修改库位")
    @Log(title = "库位", businessType = BusinessTypeEnum.UPDATE)
    public AjaxResult edit(@RequestBody BaseLocation location) {
        locationService.updateLocation(location);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('basedata:location:remove')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除库位")
    @Log(title = "库位", businessType = BusinessTypeEnum.DELETE)
    public AjaxResult remove(@PathVariable Long[] ids) {
        locationService.deleteLocation(ids);
        return AjaxResult.success();
    }

    @PostMapping("/opr")
    @Operation(summary = "库位操作")
    public AjaxResult opr(@RequestBody BaseLocationOprDTO opr) {
        locationService.locationOpr(opr);
        return AjaxResult.success();
    }


}
