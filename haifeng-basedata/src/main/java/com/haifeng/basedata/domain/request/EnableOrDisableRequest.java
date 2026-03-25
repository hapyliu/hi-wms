package com.haifeng.basedata.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 启用/停用请求模型
 * @author wangww
 * @version 1.0
 * @description TODO
 * @date 2026-03-11 15:50:56
 */
@Data
@Schema(description = "启用/停用请求模型")
public class EnableOrDisableRequest {

    /**
     * 主键id
     */
    @Schema(description= "主键id")
    @NotNull(message = "{id.not.null}")
    private Long id;

    /**
     * 属性状态 (0:启用，1:停用)
     */
    @Schema(description = "状态 (0:启用，1:停用)")
    @NotNull(message = "{status.not.null}")
    private Integer status;
}
