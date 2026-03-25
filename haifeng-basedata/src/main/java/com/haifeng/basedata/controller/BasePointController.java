package com.haifeng.basedata.controller;

import com.haifeng.basedata.domain.BasePoint;
import com.haifeng.basedata.service.IBasePointService;
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
import jakarta.servlet.http.HttpServletResponse;
import com.haifeng.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/basedata/point")
@Tag(name = "点位管理")
public class BasePointController extends BaseController {
    @Autowired
    private IBasePointService pointService;

    /**
     * 查询点位列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:point:list')")
    @GetMapping("/list")
    @Operation(summary = "点位分页列表")
    public TableDataInfo list(BasePoint point) {
        startPage();
        List<BasePoint> list = pointService.selectPointList(point);
        return getDataTable(list);
    }

    /**
     * 获取点位详细信息
     */
    @PreAuthorize("@ss.hasPermi('basedata:point:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "点位详情")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pointService.selectPointById(id));
    }

    /**
     * 新增点位
     */
    @PreAuthorize("@ss.hasPermi('basedata:point:add')")
    @PostMapping
    @Operation(summary = "新增点位")
    @Log(title = "点位", businessType = BusinessTypeEnum.INSERT)
    public AjaxResult add(@RequestBody BasePoint point) {
        pointService.insertPoint(point);
        return AjaxResult.success();
    }

    /**
     * 修改点位
     */
    @PreAuthorize("@ss.hasPermi('basedata:point:edit')")
    @PutMapping
    @Operation(summary = "修改点位")
    @Log(title = "点位", businessType = BusinessTypeEnum.UPDATE)
    public AjaxResult edit(@RequestBody BasePoint point) {
        pointService.updatePoint(point);
        return AjaxResult.success();
    }

    /**
     * 删除点位
     */
    @PreAuthorize("@ss.hasPermi('basedata:point:remove')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除点位")
    @Log(title = "点位", businessType = BusinessTypeEnum.DELETE)
    public AjaxResult remove(@PathVariable Long[] ids) {
        pointService.deletePoint(ids);
        return AjaxResult.success();
    }

    /**
     * 导出点位列表
     */
    @PreAuthorize("@ss.hasPermi('basedata:point:export')")
    @Log(title = "点位", businessType = BusinessTypeEnum.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出点位列表")
    public void export(HttpServletResponse response, BasePoint point) {
        List<BasePoint> list = pointService.selectPointList(point);
        ExcelUtil<BasePoint> util = new ExcelUtil<>(BasePoint.class);
        util.exportExcel(response, list, "点位数据");
    }

    /**
     * 下载点位导入模板
     */
    @PostMapping("/importTemplate")
    @Operation(summary = "点位导入模板")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<BasePoint> util = new ExcelUtil<>(BasePoint.class);
        util.importTemplateExcel(response, "点位数据");
    }

    /**
     * 导入点位数据
     */
    @Log(title = "点位", businessType = BusinessTypeEnum.IMPORT)
    @PreAuthorize("@ss.hasPermi('basedata:point:import')")
    @PostMapping("/importData")
    @Operation(summary = "导入点位")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<BasePoint> util = new ExcelUtil<>(BasePoint.class);
        List<BasePoint> pointList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = pointService.importPoint(pointList, updateSupport, operName);
        return AjaxResult.success(message);
    }
}
