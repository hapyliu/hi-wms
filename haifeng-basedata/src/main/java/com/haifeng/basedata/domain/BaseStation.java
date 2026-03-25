package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.BaseEntity;
import com.haifeng.common.enums.BaseStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Schema(description = "工作站")
public class BaseStation  extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 工作站编号
     */
    @Excel(name = "工作站编号")
    @NotBlank(message = "{station.code.not.blank}")
    private String stationCode;

    /** 工作站名称 */
    @Excel(name = "工作站名称")
    @NotBlank(message = "{station.name.not.blank}")
    private String stationName;

    /** 容器状态（0正常 1停用） */
    @Excel(name = "工作站状态", readConverterExp = "0=正常,1=停用")
    private String stationStatus= BaseStatusEnum.OPEN.getCode();


    @TableLogic(value = "0", delval = "2")
    @TableField(fill = FieldFill.INSERT)
    private String delFlag;
}
