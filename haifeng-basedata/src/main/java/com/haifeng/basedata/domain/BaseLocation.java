package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.BaseEntity;
import com.haifeng.common.enums.BaseStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "库位信息")
public class BaseLocation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /** 主键ID */
    private Long id;

    /** 库位编码 */
    @Excel(name = "库位编码")
    @NotBlank(message = "库位编码不能为空")
    @Schema(description = "库位编码")
    private String locationCode;

    /** 库位名称 */
    @Excel(name = "库位名称")
    @NotBlank(message = "库位名称不能为空")
    @Schema(description = "库位名称")
    private String locationName;

    /** 库位状态：0-开启1-关闭 */
    @Excel(name = "库位状态", readConverterExp = "0=开启,1=关闭")
    @Schema(description = "库位状态：0-开启1-关闭")
    private String locationStatus = BaseStatusEnum.OPEN.getCode();

    /** 长 */
    @Excel(name = "长")
    @Schema(description = "长")
    private Integer length;

    /** 宽 */
    @Excel(name = "宽")
    @Schema(description = "宽")
    private Integer width;

    /** 高 */
    @Excel(name = "高")
    @Schema(description = "高")
    private Integer height;

    /** 重量 */
    @Excel(name = "重量")
    @Schema(description = "重量")
    private Double weight;

    /** 排 */
    @Excel(name = "排")
    @Schema(description = "排")
    @TableField("`row`")
    private Integer row;

    /** 列 */
    @Excel(name = "列")
    @Schema(description = "列")
    @TableField("`column`")
    private Integer column;

    /** 层 */
    @Excel(name = "层")
    @Schema(description = "层")
    private Integer layer;

    @Excel(name = "所属楼层")
    @Schema(description = "所属楼层")
    private String floorCode;

    /** 准入容器模型 */
    @Excel(name = "准入容器模型")
    @Schema(description = "准入容器模型（多个用英文逗号隔开）")
    //新增不可为null
    private String allowContainerModel;

    @TableField(fill = FieldFill.INSERT)
    private String delFlag;

    @Schema(description = "库区编码")
    @Excel(name = "库区编码")
    private String areaCode;

    @Schema(description = "可视化区域")
    @Excel(name = "可视化区域")
    private String visualAreaCode;


    @TableField(exist = false)
    @Excel(name = "库区名称")
    @Schema(description = "库区名称")
    private String areaName;

    @TableField(exist = false)
    @Excel(name = "巷道编号")
    @Schema(description = "巷道编号")
    private String aisleCode;

    @TableField(exist = false)
    @Excel(name = "工作站编码")
    @Schema(description = "工作站编码")
    private String stationCode;

    @TableField(exist = false)
    @Excel(name = "工作站Id")
    @Schema(description = "工作站Id")
    private String stationId;

    @TableField(exist = false)
    @Schema(description = "点位关联集合")
    private List<BasePointLocation> pointLocationList;
}
