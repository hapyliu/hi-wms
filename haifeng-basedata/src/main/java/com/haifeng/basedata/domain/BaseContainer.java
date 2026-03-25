package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.CommonEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 容器对象 base_container
 * 
 * @author wangww
 * @date 2026-02-27
 */
@Data
@Builder
@Schema(description = "容器信息")
@NoArgsConstructor
@AllArgsConstructor
public class BaseContainer extends CommonEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;
    
    /** 容器编号 */
    @Excel(name = "${container.code}")
    @Schema(description = "容器编号")
    private String containerCode;
    
    /** 容器模型编号 */
    @Excel(name = "${container.model.code}")
    @Schema(description = "容器模型编号")
    private String containerModelCode;
    
    /**
     * 容器模型名称
     */
    @Excel(name = "${container.model.name}")
    @Schema(description = "容器模型名称")
    @TableField(exist = false)
    private String containerModelName;
    
    /** 位置类型：字典 dict_value */
    @Excel(name = "${position.type}", dictType = "container_position_type")
    @Schema(description = "位置类型 (字典 dict_value)")
    private String positionType;
    
    /**
     * 位置类型：字典 dict_label
     */
    @Schema(description = "位置类型 (字典 dict_label)")
    @TableField(exist = false)
    private String positionTypeDesc;
    
    /** 位置编号 (根据位置类型区别，编号不同，默认值-) */
    @Excel(name = "${position.code}")
    @Schema(description = "位置编号")
    private String positionCode;
    
    /** 工作站编号 (位置类型为库位时，假如该库位属于工作站，显示关联工作站编号) */
    @Excel(name = "${station.code}")
    @Schema(description = "工作站编号")
    private String stationCode;
    
    /** 任务编号 */
    @Excel(name = "${task.code}")
    @Schema(description = "任务编号")
    private String taskCode;
    
    /** 容器状态（0 正常 1 停用） */
    @Excel(name = "${container.status}", readConverterExp = "0=正常,1=停用")
    @Schema(description = "容器状态（0 正常 1 停用）")
    private String status;

    /** 删除标志（0 代表存在 2 代表删除） */
    @Schema(description = "删除标志（0 代表存在 2 代表删除）")
    @TableField(fill = FieldFill.INSERT)
    private String delFlag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("containerCode", getContainerCode())
            .append("containerModelCode", getContainerModelCode())
            .append("positionType", getPositionType())
            .append("positionCode", getPositionCode())
            .append("stationCode", getStationCode())
            .append("taskCode", getTaskCode())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
