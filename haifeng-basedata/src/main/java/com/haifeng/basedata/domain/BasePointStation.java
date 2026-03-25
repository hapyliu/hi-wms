package com.haifeng.basedata.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(description = "点位工作站关联")
public class BasePointStation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "工作站主键id")
    private Long stationId;

    @Schema(description = "点位主键id")
    private Long pointId;

    @Schema(description = "工作站编码")
    @TableField(exist = false)
    private String stationCode;
}
