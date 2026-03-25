package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haifeng.common.core.domain.CommonEntity;
import com.haifeng.common.group.AddGroup;
import com.haifeng.common.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 批次属性表
 * @TableName base_batch_attribute
 */
@TableName(value ="base_batch_attribute")
@Data
@Schema(description = "批次属性信息")
public class BaseBatchAttribute extends CommonEntity {
    /**
     * 主键 id
     */
    @TableId(type = IdType.AUTO)
    @Schema(description= "主键 id")
    @NotNull(message = "{batch.attr.id.not.null}", groups = {UpdateGroup.class})
    private Long id;

    /**
     * 批次属性
     */
    @NotBlank(message = "{batch.attr.code.not.blank}", groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "批次属性编码")
    private String attrCode;


    /**
     * 批次属性值
     */
    @TableField(exist = false)
    private String attrValue;

    /**
     * 属性顺序 1 到 15
     */
    @NotNull(message = "{batch.attr.index.not.null}", groups = {AddGroup.class, UpdateGroup.class})
    @Range(min = 1, max = 15, message = "{batch.attr.index.size}", groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "属性顺序 (1-15)")
    private Integer attrIndex;

    /**
     * 属性值类型:0-字符，1-数字，2-枚举，3-日期，4-日期时间
     */
    @NotBlank(message = "{batch.attr.type.not.blank}", groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "属性类型 (0-字符，1-数字，2-枚举，3-日期，4-日期时间)")
    private String attrType;

    /**
     * 属性枚举（关联 sys_dict_type 表的 dict_type 字段）
     */
    @Schema(description = "属性枚举值")
    private String attrEnum;

    /**
     * 是否必填((0=否,1=是))
     */
    @Schema(description = "是否必填((0=否,1=是))")
    @NotNull(message = "{batch.attr.required.not.blank}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer attrRequired;

    /**
     * 属性状态 (0:启用，1:停用)
     */
    @Schema(description = "状态 (0:启用，1:停用)")
    private Integer status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

}