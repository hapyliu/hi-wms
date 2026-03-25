package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haifeng.common.core.domain.CommonEntity;
import com.haifeng.common.group.AddGroup;
import com.haifeng.common.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 物料属性扩展表
 * @TableName base_material_attr_ext
 */
@TableName(value ="base_material_attr_ext")
@Data
@Schema(description = "物料属性扩展信息")
public class BaseMaterialAttrExt extends CommonEntity {
    /**
     * 主键 id
     */
    @TableId(type = IdType.AUTO)
    @Schema(description= "主键id")
    @NotNull(message = "{material.attr.id.not.null}", groups = {UpdateGroup.class})
    private Long id;

    /**
     * 物料 id(关联物料表 id)
     */
    @NotNull(message = "{material.attr.materialId.not.null}", groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "物料id")
    private Long materialId;

    /**
     * 物料属性名
     */
    @NotBlank(message = "{material.attr.name.not.blank}", groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "物料属性名")
    private String materialAttrName;

    /**
     * 物料属性值
     */
    @NotBlank(message = "{material.attr.value.not.blank}", groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "物料属性值")
    private String materialAttrValue;

    /**
     * 删除标志（0 代表存在 2 代表删除）
     */
    private String delFlag;

}