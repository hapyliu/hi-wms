package com.haifeng.common.core.domain.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.annotation.Excel.ColumnType;
import com.haifeng.common.core.domain.BaseEntity;

/**
 * 字典类型表 sys_dict_type
 * 
 * @author haifeng
 */
public class SysDictType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    @Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
    private Long dictId;

    /** 字典名称 */
    @Excel(name = "字典名称")
    private String dictName;

    /** 字典类型 */
    @Excel(name = "字典类型")
    private String dictType;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getDictId()
    {
        return dictId;
    }

    public void setDictId(Long dictId)
    {
        this.dictId = dictId;
    }

    @NotBlank(message = "{dict.name.not.blank}")
    @Size(min = 0, max = 100, message = "{dict.name.length.invalid}")
    public String getDictName()
    {
        return dictName;
    }

    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    @NotBlank(message = "{dict.type.not.blank}")
    @Size(min = 0, max = 100, message = "{dict.type.length.invalid}")
    @Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "{dict.type.pattern.invalid}")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dictId", getDictId())
            .append("dictName", getDictName())
            .append("dictType", getDictType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
