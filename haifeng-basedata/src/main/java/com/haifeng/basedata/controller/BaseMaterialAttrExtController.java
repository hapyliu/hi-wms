package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseMaterialAttrExt;
import com.haifeng.basedata.service.IBaseMaterialAttrExtService;
import com.haifeng.common.annotation.Log;
import com.haifeng.common.core.controller.BaseController;
import com.haifeng.common.core.domain.AjaxResult;
import com.haifeng.common.enums.BusinessTypeEnum;
import com.haifeng.common.group.AddGroup;
import com.haifeng.common.group.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 物料属性扩展管理
 * 
 * @author Administrator
 * @date 2026-03-11
 */
@RestController
@RequestMapping("/basedata/materialAttrExt")
@Tag(name = "物料属性扩展管理")
@Validated
public class BaseMaterialAttrExtController extends BaseController {

    @Autowired
    private IBaseMaterialAttrExtService baseMaterialAttrExtService;

    /**
     * 查询物料属性扩展列表（根据物料 ID 查询，不分页）
     */
    @PreAuthorize("@ss.hasPermi('basedata:materialAttrExt:list')")
    @GetMapping("/list")
    @Operation(summary = "查询物料属性扩展列表")
    public AjaxResult list(@RequestParam("materialId") @NotNull Long materialId) {
        List<BaseMaterialAttrExt> list = baseMaterialAttrExtService.selectByMaterialId(materialId);
        return success(list);
    }

    /**
     * 获取物料属性扩展详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:materialAttrExt:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "查询物料属性扩展详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(baseMaterialAttrExtService.getById(id));
    }

    /**
     * 新增物料属性扩展
     */
    @PreAuthorize("@ss.hasPermi('basedata:materialAttrExt:add')")
    @Log(title = "物料属性扩展", businessType = BusinessTypeEnum.INSERT)
    @PostMapping
    @Operation(summary = "新增物料属性扩展")
    public AjaxResult add(@Validated(AddGroup.class) @RequestBody BaseMaterialAttrExt baseMaterialAttrExt) {
        return toAjax(baseMaterialAttrExtService.insertBaseMaterialAttrExt(baseMaterialAttrExt));
    }

    /**
     * 修改物料属性扩展
     */
    @PreAuthorize("@ss.hasPermi('basedata:materialAttrExt:edit')")
    @Log(title = "物料属性扩展", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping
    @Operation(summary = "修改物料属性扩展")
    public AjaxResult edit(@Validated(UpdateGroup.class) @RequestBody BaseMaterialAttrExt baseMaterialAttrExt) {
        return AjaxResult.success(baseMaterialAttrExtService.updateBaseMaterialAttrExt(baseMaterialAttrExt));
    }

    /**
     * 删除物料属性扩展
     */
    @PreAuthorize("@ss.hasPermi('basedata:materialAttrExt:remove')")
    @Log(title = "物料属性扩展", businessType = BusinessTypeEnum.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除物料属性扩展")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(baseMaterialAttrExtService.removeByIds(Arrays.asList(ids)));
    }
}
