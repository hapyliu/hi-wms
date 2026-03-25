package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.CommonEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 仓库表
 * @TableName base_warehouse
 */
@TableName(value ="base_warehouse")
@Data
@Schema(description = "仓库信息")
public class BaseWarehouse extends CommonEntity {
    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    /**
     * 仓库编号
     */
    @Excel(name = "${warehouse.code}")
    @NotBlank(message = "{warehouse.code.not.blank}")
    @Schema(description = "仓库编号")
    private String warehouseCode;

    /**
     * 仓库名称
     */
    @Excel(name = "${warehouse.name}")
    @NotBlank(message = "{warehouse.name.not.blank}")
    @Schema(description = "仓库名称")
    private String warehouseName;

    /**
     * 仓库描述
     */
    @Excel(name = "${warehouse.desc}")
    @Schema(description = "仓库描述")
    private String warehouseDesc;

    /**
     * 删除标志（0 代表存在 2 代表删除）
     */
    @Schema(description = "删除标志（0 代表存在 2 代表删除）")
    private String delFlag;

}
