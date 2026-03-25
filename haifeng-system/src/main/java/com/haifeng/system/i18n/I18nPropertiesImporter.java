package com.haifeng.system.i18n;

import com.haifeng.common.utils.DateUtils;
import com.haifeng.system.domain.SysI18nMessage;
import com.haifeng.system.service.ISysI18nMessageService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 国际化消息导入工具
 * 支持Excel文件导入（.xlsx 和 .xls）
 */
@Component
public class I18nPropertiesImporter {

    private static final Logger logger = Logger.getLogger(I18nPropertiesImporter.class.getName());

    private final ISysI18nMessageService messageService;

    public I18nPropertiesImporter(ISysI18nMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 从Excel文件导入消息
     *
     * @param file         Excel文件
     * @param languageCode 语言代码
     * @param overwrite    是否覆盖已存在的消息
     * @return 导入的消息数量
     */
    public int importFromExcel(MultipartFile file, String languageCode, boolean overwrite) throws IOException {
        List<I18nMessageExcelData> dataList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = createWorkbook(file.getOriginalFilename(), inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String messageKey = getCellValue(row, 0);
                String messageValue = getCellValue(row, 1);
                String category = getCellValue(row, 2);
                String remark = getCellValue(row, 3);

                if (messageKey != null && !messageKey.trim().isEmpty() &&
                    messageValue != null && !messageValue.trim().isEmpty()) {
                    I18nMessageExcelData data = new I18nMessageExcelData();
                    data.setMessageKey(messageKey);
                    data.setMessageValue(messageValue);
                    data.setCategory(category);
                    data.setRemark(remark);
                    dataList.add(data);
                }
            }

            workbook.close();
        }

        return processMessages(dataList, languageCode, overwrite);
    }

    /**
     * 处理消息数据
     */
    private int processMessages(List<I18nMessageExcelData> dataList, String languageCode, boolean overwrite) {
        List<SysI18nMessage> messages = new ArrayList<>();
        int importCount = 0;

        for (I18nMessageExcelData data : dataList) {
            String messageKey = data.getMessageKey();
            String messageValue = data.getMessageValue();

            if (messageKey == null || messageKey.trim().isEmpty() ||
                messageValue == null || messageValue.trim().isEmpty()) {
                logger.fine("Skipping empty row");
                continue;
            }

            // Extract category from key if not provided
            String category = data.getCategory();
            if (category == null || category.trim().isEmpty()) {
                category = extractCategory(messageKey);
            }

            // Check if message already exists
            SysI18nMessage existing = messageService.selectMessageByKeyAndLanguage(messageKey, languageCode);

            if (existing != null && !overwrite) {
                logger.fine("Message already exists, skipping: " + messageKey);
                continue;
            }

            SysI18nMessage message = new SysI18nMessage();
            message.setMessageKey(messageKey);
            message.setMessageValue(messageValue);
            message.setLanguageCode(languageCode);
            message.setCategory(category);
            message.setStatus(1);
            message.setCreateBy("system");
            message.setCreateTime(DateUtils.getNowDate());

            if (existing != null && overwrite) {
                message.setMessageId(existing.getMessageId());
                message.setUpdateBy("system");
                message.setUpdateTime(DateUtils.getNowDate());
                messageService.updateMessage(message);
            } else {
                messages.add(message);
            }

            importCount++;
        }

        // Batch insert new messages
        if (!messages.isEmpty()) {
            messageService.batchInsertMessages(messages);
        }

        logger.info("Successfully imported " + importCount + " messages for language: " + languageCode);
        return importCount;
    }

    /**
     * 创建Workbook对象
     */
    private Workbook createWorkbook(String filename, InputStream inputStream) throws IOException {
        if (filename != null && filename.endsWith(".xls")) {
            return new HSSFWorkbook(inputStream);
        } else {
            return new XSSFWorkbook(inputStream);
        }
    }

    /**
     * 获取单元格值
     */
    private String getCellValue(Row row, int columnIndex) {
        if (row == null) {
            return null;
        }

        try {
            var cell = row.getCell(columnIndex);
            if (cell == null) {
                return null;
            }

            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    return String.valueOf((long) cell.getNumericCellValue());
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                default:
                    return null;
            }
        } catch (Exception e) {
            logger.warning("Error reading cell value: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extract category from message key
     * e.g., "validation.required" -> "validation"
     */
    private String extractCategory(String messageKey) {
        if (messageKey == null || !messageKey.contains(".")) {
            return "default";
        }

        int dotIndex = messageKey.indexOf(".");
        return messageKey.substring(0, dotIndex);
    }

    /**
     * Excel数据模型
     */
    public static class I18nMessageExcelData {
        private String messageKey;
        private String messageValue;
        private String category;
        private String remark;

        public String getMessageKey() {
            return messageKey;
        }

        public void setMessageKey(String messageKey) {
            this.messageKey = messageKey;
        }

        public String getMessageValue() {
            return messageValue;
        }

        public void setMessageValue(String messageValue) {
            this.messageValue = messageValue;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
