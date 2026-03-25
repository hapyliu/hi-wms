package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.CommonEntity;
import com.haifeng.common.group.AddGroup;
import com.haifeng.common.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 容器模型表
 * @TableName base_container_model
 */
@TableName(value ="base_container_model")
@Data
@Schema(description = "容器模型信息")
public class BaseContainerModel extends CommonEntity {
    
    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    /**
     * 容器模型编号
     */
    @Excel(name = "${container.model.code}")
    @NotBlank(message = "{container.model.code.not.blank}", groups = {AddGroup.class})
    @Schema(description = "容器模型编号")
    private String containerModelCode;

    /**
     * 容器模型名称
     */
    @Excel(name = "${container.model.name}")
    @NotBlank(message = "{container.model.name.not.blank}", groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "容器模型名称")
    private String containerModelName;

    /**
     * 容器基础类型：字典 dict_value
     */
    @Excel(name = "${container.basic.type}", dictType = "container_basic_type")
    @NotBlank(message = "{container.basic.type.not.blank}", groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "容器基础类型 (字典 dict_value)")
    private String containerBasicType;

    /**
     * 容器基础类型：字典 dict_label
     */
    @TableField(exist = false)
    @Schema(description = "容器基础类型 (字典 dict_label)")
    private String containerBasicTypeDesc;

    /**
     * 长 (单位:mm)
     */
    @Excel(name = "${length}")
    @Schema(description = "长度 (mm)")
    private Integer length;

    /**
     * 宽 (单位:mm)
     */
    @Excel(name = "${width}")
    @Schema(description = "宽度 (mm)")
    private Integer width;

    /**
     * 高 (单位:mm)
     */
    @Excel(name = "${height}")
    @Schema(description = "高度 (mm)")
    private Integer height;

    /**
     * 货架面数 (枚举值：0，2，4，默认 0)
     */
    @Excel(name = "${shelf.face.count}")
    @Schema(description = "货架面数")
    private Integer shelfFaceCount;

    /**
     * 货架面 (枚举值：A,B,C,D)
     */
    @Excel(name = "${shelf.face}")
    @Schema(description = "货架面")
    private String shelfFace;

    /**
     * 格口层数
     */
    @Excel(name = "${grid.floor.count}")
    @Schema(description = "格口层数")
    private Integer gridFloorCount;

    /**
     * 单层格口数
     */
    @Excel(name = "${single.grid.count}")
    @Schema(description = "单层格口数")
    private Integer singleGridCount;

    /**
     * 删除标志（0 代表存在 2 代表删除）
     */
    @Schema(description = "删除标志（0 代表存在 2 代表删除）")
    private String delFlag;

}
