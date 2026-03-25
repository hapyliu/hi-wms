package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseApp;
import com.haifeng.basedata.service.IBaseAppService;
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
@RequestMapping("/basedata/app")
@Tag(name = "应用管理")
public class BaseAppController extends BaseController {

    @Autowired
    private IBaseAppService appService;

    /**
     * 查询应用列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:app:list')")
    @GetMapping("/list")
    @Operation(summary = "查询应用分页列表")
    public TableDataInfo list(BaseApp app) {
        startPage();
        List<BaseApp> list = appService.selectAppList(app);
        return getDataTable(list);
    }

    /**
     * 获取应用详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:app:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询应用详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(appService.selectAppById(id));
    }

    /**
     * 新增应用
     */
    @PreAuthorize("@ss.hasPermi('basedata:app:add')")
    @PostMapping
    @Operation(summary = "新增应用")
    @Log(title = "应用", businessType = BusinessTypeEnum.INSERT)
    public AjaxResult add(@Validated @RequestBody BaseApp app) {
        appService.insertApp(app);
        return AjaxResult.success();
    }

    /**
     * 修改应用
     */
    @PreAuthorize("@ss.hasPermi('basedata:app:edit')")
    @PutMapping
    @Operation(summary = "修改应用")
    @Log(title = "应用", businessType = BusinessTypeEnum.UPDATE)
    public AjaxResult edit(@Validated @RequestBody BaseApp app) {
        appService.updateApp(app);
        return AjaxResult.success();
    }

    /**
     * 删除应用
     */
    @PreAuthorize("@ss.hasPermi('basedata:app:remove')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除应用")
    @Log(title = "应用", businessType = BusinessTypeEnum.DELETE)
    public AjaxResult remove(@PathVariable Long[] ids) {
        appService.deleteApp(ids);
        return AjaxResult.success();
    }
}
