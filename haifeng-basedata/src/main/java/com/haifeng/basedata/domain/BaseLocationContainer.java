package com.haifeng.basedata.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 库位容器关联信息
 */
@Data
@Builder
public class BaseLocationContainer  implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 库位编码 */
    @Schema(description = "库位编码")
    private String locationCode;

    /** 容器编码 */
    @Schema(description = "容器编码")
    private String containerCode;
}
