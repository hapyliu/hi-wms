package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.BaseEntity;
import com.haifeng.common.enums.BaseStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "点位信息")
public class BasePoint extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 点位编码 */
    @Excel(name = "点位编码")
    @NotBlank(message = "point.code.not.blank")
    @Schema(description = "点位编码")
    private String pointCode;

    /** 设备供应商编码 */
    @Excel(name = "应用编码")
    @NotBlank(message = "app.code.not.blank")
    @Schema(description = "应用编码")
    private String appCode;

    /** 点位名称 */
    @Excel(name = "点位名称")
    @NotBlank(message = "point.name.not.blank")
    @Schema(description = "点位名称")
    private String pointName;

    /** 点位类型：0-库位1-路点2-充电3-障碍 */
    @Excel(name = "点位类型", readConverterExp = "0=库位,1=路点,2=充电,3=障碍")
    @Schema(description = "点位类型：0-库位1-路点2-充电3-障碍")
    private String pointType;

    /** 点位状态：0-开启1-关闭 */
    @Excel(name = "点位状态", readConverterExp = "0=开启,1=关闭")
    @Schema(description = "点位状态：0-开启1-关闭")
    private String pointStatus = BaseStatusEnum.OPEN.getCode();

    /** x坐标 */
    @Excel(name = "x坐标")
    @Schema(description = "x坐标")
    private String coordX;

    /** y坐标 */
    @Excel(name = "y坐标")
    @Schema(description = "y坐标")
    private String coordY;

    /** z坐标 */
    @Excel(name = "z坐标")
    @Schema(description = "z坐标")
    private String coordZ;

    /** 相对x坐标 */
    @Excel(name = "相对x坐标")
    @Schema(description = "相对x坐标")
    private String relCoordX;

    /** 相对y坐标 */
    @Excel(name = "相对y坐标")
    @Schema(description = "相对y坐标")
    private String relCoordY;

    /** 相对z坐标 */
    @Excel(name = "相对z坐标")
    @Schema(description = "相对z坐标")
    private String relCoordZ;

    /** 方向：0,90,180,270 */
    @Excel(name = "方向")
    @Schema(description = "方向：0,90,180,270")
    private Integer rackDir;

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

    @TableLogic(value = "0", delval = "2")
    @TableField(fill = FieldFill.INSERT)
    private String delFlag;

    @TableField(exist = false)
    @Schema(description = "工作站ID")
    private Long stationId;

    @TableField(exist = false)
    @Schema(description = "工作站编码")
    private String stationCode;
}
