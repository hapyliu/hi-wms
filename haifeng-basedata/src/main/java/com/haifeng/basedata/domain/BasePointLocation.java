package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.haifeng.common.annotation.Excel;
import com.haifeng.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "点位库位关联")
public class BasePointLocation {
    private static final long serialVersionUID = 1L;


    @Schema(description = "点位Id")
    private Long pointId;


    @Schema(description = "库位Id")
    private Long locationId;


    @Schema(description = "关系类型0-库位重合1-进入申请2-退出申请3-排队等待4-入库关联5-出库关联6-出入库关联")
    private String pointLocationType;

    @TableField(exist = false)
    private String pointCode;

    @TableField(exist = false)
    private String appCode;
}
