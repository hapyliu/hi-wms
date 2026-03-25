package com.haifeng.system.domain;

import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 国际化消息表
 */
public class SysI18nMessage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 消息ID */
    private Long messageId;

    /** 消息键 */
    @Excel(name = "消息键")
    @NotBlank(message = "消息键不能为空")
    @Size(min = 1, max = 100, message = "消息键长度必须在1到100个字符之间")
    private String messageKey;

    /** 消息值 */
    @Excel(name = "消息值")
    @NotBlank(message = "消息值不能为空")
    @Size(min = 1, max = 1000, message = "消息值长度必须在1到1000个字符之间")
    private String messageValue;

    /** 语言代码 */
    @Excel(name = "语言代码")
    @NotBlank(message = "语言代码不能为空")
    private String languageCode;

    /** 消息分类 */
    @Excel(name = "消息分类")
    @Size(max = 50, message = "消息分类长度不能超过50个字符")
    private String category;

    /** 是否启用（0否 1是） */
    @Excel(name = "是否启用", readConverterExp = "0=否,1=是")
    private Integer status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

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

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SysI18nMessage{" +
                "messageId=" + messageId +
                ", messageKey='" + messageKey + '\'' +
                ", messageValue='" + messageValue + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", category='" + category + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}
