package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BaseContainer;
import com.haifeng.basedata.domain.request.ContainerBatchCreateRequest;
import com.haifeng.basedata.service.IBaseContainerService;
import com.haifeng.common.annotation.Log;
import com.haifeng.common.core.controller.BaseController;
import com.haifeng.common.core.domain.AjaxResult;
import com.haifeng.common.core.page.TableDataInfo;
import com.haifeng.common.enums.BusinessTypeEnum;
import com.haifeng.common.utils.DictUtils;
import com.haifeng.common.utils.MessageUtils;
import com.haifeng.common.utils.poi.ExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 容器 Controller
 * 
 * @author wangww
 * @date 2026-02-27
 */
@RestController
@RequestMapping("/basedata/container")
@Tag(name = "容器管理")
public class BaseContainerController extends BaseController
{
    @Autowired
    private IBaseContainerService baseContainerService;

    /**
     * 查询容器列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:container:list')")
    @GetMapping("/list")
    @Operation(summary = "查询容器列表")
    public TableDataInfo list(BaseContainer baseContainer)
    {
        startPage();
        List<BaseContainer> list = baseContainerService.selectBaseContainerList(baseContainer);
        for (BaseContainer container : list) {
            container.setPositionTypeDesc(DictUtils.getDictLabel("container_position_type", container.getPositionType()));
        }
        return getDataTable(list);
    }

    /**
     * 导出容器列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:container:export')")
    @Log(title = "容器", businessType = BusinessTypeEnum.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出容器列表")
    public void export(HttpServletResponse response, BaseContainer baseContainer)
    {
        List<BaseContainer> list = baseContainerService.selectBaseContainerList(baseContainer);
        ExcelUtil<BaseContainer> util = new ExcelUtil<BaseContainer>(BaseContainer.class);
        util.exportExcel(response, list, MessageUtils.message("container.export.sheet.name"));
    }

    /**
     * 获取容器详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:container:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "获取容器详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baseContainerService.selectBaseContainerById(id));
    }

    /**
     * 新增容器
     */
    /*@PreAuthorize("@ss.hasPermi('basedata:container:add')")
    @Log(title = "容器", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BaseContainer baseContainer)
    {
        if (!baseContainerService.checkContainerCodeUnique(baseContainer))
        {
            return error(MessageUtils.message("container.add.error", baseContainer.getContainerCode()));
        }
        // 判断容器模型编号是否存在
        if (!baseContainerService.checkContainerModelCodeExists(baseContainer.getContainerModelCode()))
        {
            return error(MessageUtils.message("container.model.code.not.exists", baseContainer.getContainerModelCode()));
        }
        baseContainer.setCreateBy(getUsername());
        return toAjax(baseContainerService.insertBaseContainer(baseContainer));
    }*/

    /**
     * 修改容器
     */
    /*@PreAuthorize("@ss.hasPermi('basedata:container:edit')")
    @Log(title = "容器", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BaseContainer baseContainer)
    {
        if (!baseContainerService.checkContainerCodeUnique(baseContainer))
        {
            return error(MessageUtils.message("container.update.error", baseContainer.getContainerCode()));
        }
        if (!baseContainerService.checkContainerModelCodeExists(baseContainer.getContainerModelCode()))
        {
            return error(MessageUtils.message("container.model.code.not.exists", baseContainer.getContainerModelCode()));
        }
        baseContainer.setUpdateBy(getUsername());
        return toAjax(baseContainerService.updateBaseContainer(baseContainer));
    }*/

    /**
     * 删除容器
     */
    @PreAuthorize("@ss.hasPermi('basedata:container:remove')")
    @Log(title = "容器", businessType = BusinessTypeEnum.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baseContainerService.deleteBaseContainerByIds(ids));
    }

    /**
     * 批量创建容器
     */
    @PreAuthorize("@ss.hasPermi('basedata:container:add')")
    @Log(title = "容器批量创建", businessType = BusinessTypeEnum.INSERT)
    @PostMapping("/batch")
    @Operation(summary = "批量创建容器")
    public AjaxResult batchCreate(@Validated @RequestBody ContainerBatchCreateRequest request)
    {
        int count = baseContainerService.batchCreateContainers(request);
        return success("成功创建 " + count + " 个容器");
    }
}
