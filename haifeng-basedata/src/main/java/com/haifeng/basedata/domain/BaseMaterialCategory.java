package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.CommonEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "物料分类")
public class BaseMaterialCategory extends CommonEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;
    
    /** 物料分类编码 */
    @Excel(name = "${material.category.code}")
    @NotBlank(message = "{material.category.code.not.blank}")
    @Schema(description = "物料分类编码")
    private String categoryCode;
    
    /** 物料分类名称 */
    @Excel(name = "${material.category.name}")
    @NotBlank(message = "{material.category.name.not.blank}")
    @Schema(description = "物料分类名称")
    private String categoryName;
    
    /** 父分类 id */
    @Schema(description = "父分类 ID")
    private Long parentId;
        
    /** 祖级列表（用逗号分隔） */
    @Schema(description = "祖级列表（用逗号分隔）")
    private String ancestors;

    /** 删除标志（0 代表存在 2 代表删除） */
    @Schema(description = "删除标志（0 代表存在 2 代表删除）")
    private String delFlag;
}
