package com.haifeng.basedata.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Schema(description = "库位工作站关联")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseLocationStation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "库位主键id")
    private Long locationId;

    @Schema(description = "工作站主键id")
    private Long stationId;
}
