package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "可视化区域")
public class BaseVisualArea extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 可视化区域编号 */
    @Excel(name = "可视化区域编号")
    @NotBlank(message = "{visualArea.code.not.blank}")
    private String visualAreaCode;

    /** 可视化区域名称 */
    @Excel(name = "可视化区域名称")
    @NotBlank(message = "{visualArea.name.not.blank}")
    private String visualAreaName;

    /** 中心坐标X */
    @Excel(name = "中心坐标X")
    private String coordX;

    /** 中心坐标Y */
    @Excel(name = "中心坐标Y")
    private String coordY;

    /** 长度 */
    @Excel(name = "长度")
    private String length;

    /** 宽度 */
    @Excel(name = "宽度")
    private String width;

    /** 删除标志（0代表存在 2代表删除） */
    @TableLogic(value = "0", delval = "2")
    @TableField(fill = FieldFill.INSERT)
    private String delFlag;

}
