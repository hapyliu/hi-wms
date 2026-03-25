package com.haifeng.web.controller.system;

import com.haifeng.common.annotation.Log;
import com.haifeng.common.core.controller.BaseController;
import com.haifeng.common.core.domain.AjaxResult;
import com.haifeng.common.core.page.TableDataInfo;
import com.haifeng.common.enums.BusinessTypeEnum;
import com.haifeng.system.domain.SysI18nMessage;
import com.haifeng.system.i18n.I18nPropertiesImporter;
import com.haifeng.system.service.ISysI18nMessageService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 国际化消息 Controller
 */
@RestController
@RequestMapping("/system/i18n/message")
public class SysI18nMessageController extends BaseController {

    private static final Logger logger = Logger.getLogger(SysI18nMessageController.class.getName());

    private final ISysI18nMessageService messageService;
    private final I18nPropertiesImporter importer;

    public SysI18nMessageController(ISysI18nMessageService messageService, I18nPropertiesImporter importer) {
        this.messageService = messageService;
        this.importer = importer;
    }

    /**
     * 查询消息列表
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysI18nMessage message) {
        startPage();
        List<SysI18nMessage> list = messageService.selectMessageList(message);
        return getDataTable(list);
    }

    /**
     * 获取消息详情
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:message:query')")
    @GetMapping("/{messageId}")
    public AjaxResult getInfo(@PathVariable Long messageId) {
        SysI18nMessage message = messageService.selectMessageById(messageId);
        return AjaxResult.success(message);
    }

    /**
     * 新增消息
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:message:add')")
    @Log(title = "国际化消息", businessType = BusinessTypeEnum.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysI18nMessage message) {
        message.setCreateBy(getUsername());
        int rows = messageService.insertMessage(message);
        return toAjax(rows);
    }

    /**
     * 修改消息
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:message:edit')")
    @Log(title = "国际化消息", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysI18nMessage message) {
        message.setUpdateBy(getUsername());
        int rows = messageService.updateMessage(message);
        return toAjax(rows);
    }

    /**
     * 删除消息
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:message:remove')")
    @Log(title = "国际化消息", businessType = BusinessTypeEnum.DELETE)
    @DeleteMapping("/{messageIds}")
    public AjaxResult remove(@PathVariable Long[] messageIds) {
        int rows = messageService.deleteMessageByIds(messageIds);
        return toAjax(rows);
    }

    /**
     * 导入Excel文件
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:message:import')")
    @Log(title = "国际化消息导入", businessType = BusinessTypeEnum.IMPORT)
    @PostMapping("/import")
    public AjaxResult importMessages(@RequestParam("file") MultipartFile file,
                                     @RequestParam String languageCode,
                                     @RequestParam(required = false) Integer overwrite) {
        try {
            if (file == null || file.isEmpty()) {
                return AjaxResult.error("请选择要导入的文件");
            }

            String filename = file.getOriginalFilename();
            if (filename == null || (!filename.endsWith(".xlsx") && !filename.endsWith(".xls"))) {
                return AjaxResult.error("只支持Excel文件（.xlsx 或 .xls）");
            }

            int count = importer.importFromExcel(file, languageCode, overwrite == 1);
            return AjaxResult.success("导入成功，共导入 " + count + " 条消息");
        } catch (Exception e) {
            return AjaxResult.error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 下载导入模板
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:message:import')")
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("消息模板");

            // 创建表头
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("messageKey");
            headerRow.createCell(1).setCellValue("messageValue");
            headerRow.createCell(2).setCellValue("category");
            headerRow.createCell(3).setCellValue("remark");

            // 添加示例数据
            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("user.not.exists");
            row1.createCell(1).setCellValue("用户不存在或密码错误");
            row1.createCell(2).setCellValue("error");
            row1.createCell(3).setCellValue("登录错误提示");

            Row row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue("user.login.success");
            row2.createCell(1).setCellValue("登录成功");
            row2.createCell(2).setCellValue("system");
            row2.createCell(3).setCellValue("登录成功提示");

            Row row3 = sheet.createRow(3);
            row3.createCell(0).setCellValue("validation.required");
            row3.createCell(1).setCellValue("* 必须填写");
            row3.createCell(2).setCellValue("validation");
            row3.createCell(3).setCellValue("必填字段验证");

            // 设置列宽
            sheet.setColumnWidth(0, 25 * 256);
            sheet.setColumnWidth(1, 30 * 256);
            sheet.setColumnWidth(2, 15 * 256);
            sheet.setColumnWidth(3, 25 * 256);

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("i18n_message_template.xlsx", StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            // 写入Excel
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            logger.severe("Failed to download template: " + e.getMessage());
            response.setStatus(500);
        }
    }

    /**
     * 获取支持的语言列表
     */
    @GetMapping("/languages")
    public AjaxResult getSupportedLanguages() {
        List<String> languages = messageService.getSupportedLanguages();
        return AjaxResult.success(languages);
    }

    /**
     * 获取消息分类列表
     */
    @GetMapping("/categories")
    public AjaxResult getCategories() {
        List<Map<String, String>> categories = messageService.getCategories();
        return AjaxResult.success(categories);
    }
}
