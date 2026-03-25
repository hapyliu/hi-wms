package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseBatchAttribute;
import com.haifeng.basedata.domain.request.EnableOrDisableRequest;
import com.haifeng.basedata.service.IBaseBatchAttributeService;
import com.haifeng.common.annotation.Log;
import com.haifeng.common.core.controller.BaseController;
import com.haifeng.common.core.domain.AjaxResult;
import com.haifeng.common.core.page.TableDataInfo;
import com.haifeng.common.enums.BusinessTypeEnum;
import com.haifeng.common.enums.EnableOrDisableEnum;
import com.haifeng.common.group.AddGroup;
import com.haifeng.common.group.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 批次属性管理 Controller
 * 
 * @author Administrator
 * @date 2026-03-11
 */
@RestController
@RequestMapping("/basedata/batchAttribute")
@Tag(name = "批次属性管理")
public class BaseBatchAttributeController extends BaseController {

    @Autowired
    private IBaseBatchAttributeService baseBatchAttributeService;

    /**
     * 查询批次属性列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:batchAttribute:list')")
    @GetMapping("/list")
    @Operation(summary = "查询批次属性列表")
    public TableDataInfo list(BaseBatchAttribute baseBatchAttribute) {
        startPage();
        List<BaseBatchAttribute> list = baseBatchAttributeService.selectBaseBatchAttributeList(baseBatchAttribute);
        return getDataTable(list);
    }

    /**
     * 查询批次属性列表(不分页)
     */
    @PreAuthorize("@ss.hasPermi('basedata:batchAttribute:list')")
    @GetMapping("/queryList")
    @Operation(summary = "查询批次属性列表(不分页)")
    public AjaxResult queryList() {
        BaseBatchAttribute batchAttribute = new BaseBatchAttribute();
        batchAttribute.setStatus(EnableOrDisableEnum.ENABLE.getValue());
        List<BaseBatchAttribute> list = baseBatchAttributeService.selectBaseBatchAttributeList(batchAttribute);
        return AjaxResult.success(list);
    }

    /**
     * 获取批次属性详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:batchAttribute:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询批次属性详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(baseBatchAttributeService.getById(id));
    }

    /**
     * 新增批次属性
     */
    @PreAuthorize("@ss.hasPermi('basedata:batchAttribute:add')")
    @Log(title = "批次属性", businessType = BusinessTypeEnum.INSERT)
    @PostMapping
    @Operation(summary = "新增批次属性")
    public AjaxResult add(@Validated(AddGroup.class) @RequestBody BaseBatchAttribute baseBatchAttribute) {
        return toAjax(baseBatchAttributeService.insertBaseBatchAttribute(baseBatchAttribute));
    }

    /**
     * 修改批次属性
     */
    @PreAuthorize("@ss.hasPermi('basedata:batchAttribute:edit')")
    @Log(title = "批次属性", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping
    @Operation(summary = "修改批次属性")
    public AjaxResult edit(@Validated(UpdateGroup.class) @RequestBody BaseBatchAttribute baseBatchAttribute) {
        return toAjax(baseBatchAttributeService.updateBaseBatchAttribute(baseBatchAttribute));
    }

    /**
     * 删除批次属性
     */
    @PreAuthorize("@ss.hasPermi('basedata:batchAttribute:remove')")
    @Log(title = "批次属性", businessType = BusinessTypeEnum.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除批次属性")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(baseBatchAttributeService.deleteBaseBatchAttributeByIds(ids));
    }

    /**
     * 状态修改（启用/停用）
     */
    @PreAuthorize("@ss.hasPermi('basedata:batchAttribute:edit')")
    @Log(title = "批次属性", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping("/changeStatus")
    @Operation(summary = "修改批次属性状态")
    public AjaxResult changeStatus(@RequestBody @Valid EnableOrDisableRequest enableOrDisableRequest) {
        return toAjax(baseBatchAttributeService.changeStatus(enableOrDisableRequest));
    }
}
