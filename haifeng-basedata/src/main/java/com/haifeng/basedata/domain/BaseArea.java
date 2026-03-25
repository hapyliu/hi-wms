package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "库区")
public class BaseArea extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 工作站编号 */
    @Excel(name = "区域编号")
    @NotBlank(message = "{area.code.not.blank}")
    private String areaCode;

    /** 工作站名称 */
    @Excel(name = "区域名称")
    @NotBlank(message = "{area.name.not.blank}")
    private String areaName;

    @Excel(name = "仓库编码")
    private String warehouseCode;

    @Excel(name = "仓库名称")
    private String warehouseName;


    @TableLogic(value = "0", delval = "2")
    @TableField(fill = FieldFill.INSERT)
    private String delFlag;

}
