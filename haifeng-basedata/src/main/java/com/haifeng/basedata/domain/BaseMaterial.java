package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.CommonEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "物料信息")
public class BaseMaterial extends CommonEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    /** 物料编码 */
    @Excel(name = "${material.code}")
    @NotBlank(message = "{material.code.not.blank}")
    @Schema(description = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "${material.name}")
    @NotBlank(message = "{material.name.not.blank}")
    @Schema(description = "物料名称")
    private String materialName;

    /** 物料条码 */
    @Excel(name = "${material.barcode}")
    @Schema(description = "物料条码")
    private String barCode;

    /** 物料分类 id */
    @NotNull(message = "{material.category.id.not.blank}")
    @Schema(description = "物料分类 ID")
    private Long categoryId;

    /** 物料分类名称 */
    @Excel(name = "${material.category.name}")
    @TableField(exist = false)
    @Schema(description = "物料分类名称")
    private String categoryName;

    /** 规格型号 */
    @Excel(name = "${material.specification}")
    @Schema(description = "规格型号")
    private String specification;

    /** 单位 */
    @Excel(name = "${material.unit}")
    @Schema(description = "单位")
    private String unit;

    /** 长度 (单位 mm) */
    @Excel(name = "${material.length}")
    @Schema(description = "长度 (mm)")
    private Integer length;

    /** 高度 (单位 mm) */
    @Excel(name = "${material.height}")
    @Schema(description = "高度 (mm)")
    private Integer height;

    /** 宽度 (单位 mm) */
    @Excel(name = "${material.width}")
    @Schema(description = "宽度 (mm)")
    private Integer width;

    /** 体积（根据长宽高计算，单位立方 mm） */
    @Excel(name = "${material.volume}")
    @Schema(description = "体积（立方 mm）")
    private Integer volume;

    /** 毛重 (单位 kg) */
    @Excel(name = "${material.gross.weight}")
    @Schema(description = "毛重 (kg)")
    private Double grossWeight;

    /** 净重 (单位 kg) */
    @Excel(name = "${material.net.weight}")
    @Schema(description = "净重 (kg)")
    private Double netWeight;

    /** 保质期 (天) */
    @Excel(name = "${material.shelf.life}")
    @Schema(description = "保质期 (天)")
    private Integer shelfLife;

    /** 是否 sn(0-否 1-是) */
    @Excel(name = "${material.is.sn.managed}", readConverterExp = "0=否，1=是")
    @Schema(description = "是否 SN 管理 (0-否 1-是)")
    private Integer isSnManaged;
    
    /** 是否批次管理 (0-否 1-是) */
    @Excel(name = "${material.is.batch.managed}", readConverterExp = "0=否，1=是")
    @Schema(description = "是否批次管理 (0-否 1-是)")
    private Integer isBatchManaged;
    
    /** 扩展属性名（用于查询） */
    @TableField(exist = false)
    @Schema(description = "扩展属性名（用于查询）")
    private String attrName;
    
    /** 扩展属性值（用于查询） */
    @TableField(exist = false)
    @Schema(description = "扩展属性值（用于查询）")
    private String attrValue;
    
    /** 删除标志（0 代表存在 2 代表删除） */
    @Schema(description = "删除标志（0 代表存在 2 代表删除）")
    private String delFlag;
}
