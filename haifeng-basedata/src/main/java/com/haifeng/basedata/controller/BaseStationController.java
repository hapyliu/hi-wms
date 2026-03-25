package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseStation;
import com.haifeng.basedata.service.IBaseStationService;
import com.haifeng.common.annotation.Log;
import com.haifeng.common.core.controller.BaseController;
import com.haifeng.common.core.domain.AjaxResult;
import com.haifeng.common.core.page.TableDataInfo;
import com.haifeng.common.enums.BusinessTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/basedata/station")
@Tag(name = "工作站管理")
public class BaseStationController extends BaseController{
    @Autowired
    private IBaseStationService stationService;

    /**
     * 查询工作站列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:station:list')")
    @GetMapping("/list")
    @Operation(summary = "工作站分页列表")
    public TableDataInfo list(BaseStation station) {
        startPage();
        List<BaseStation> list = stationService.selectStationList(station);
        return getDataTable(list);
    }

    /**
     * 获取工作站详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:station:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "工作站详情")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(stationService.selectStationById(id));
    }

    /**
     * 新增工作站
     */
    @PreAuthorize("@ss.hasPermi('basedata:station:add')")
    @PostMapping
    @Operation(summary = "新增工作站")
    @Log(title = "工作站", businessType = BusinessTypeEnum.INSERT)
    public AjaxResult add(@RequestBody BaseStation station) {
        stationService.insertStation(station);
        return AjaxResult.success();
    }

    /**
     * 修改工作站
     */
    @PreAuthorize("@ss.hasPermi('basedata:station:edit')")
    @PutMapping
    @Operation(summary = "修改工作站")
    @Log(title = "工作站", businessType = BusinessTypeEnum.UPDATE)
    public AjaxResult edit(@RequestBody BaseStation station) {
        stationService.updateStation(station);
        return AjaxResult.success();
    }

    /**
     * 删除工作站
     */
    @PreAuthorize("@ss.hasPermi('basedata:station:remove')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除工作站")
    @Log(title = "工作站", businessType = BusinessTypeEnum.DELETE)
    public AjaxResult remove(@PathVariable Long[] ids) {
        stationService.deleteStation(ids);
        return AjaxResult.success();
    }
}

